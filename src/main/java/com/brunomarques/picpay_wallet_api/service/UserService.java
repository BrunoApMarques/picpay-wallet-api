package com.brunomarques.picpay_wallet_api.service;


import com.brunomarques.picpay_wallet_api.domain.User;
import com.brunomarques.picpay_wallet_api.dto.CreateUserRequest;
import com.brunomarques.picpay_wallet_api.dto.UserResponse;
import com.brunomarques.picpay_wallet_api.exception.UserNotFoundException;
import com.brunomarques.picpay_wallet_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // 1) Regra: não permitir documento duplicado
        userRepository.findByDocument(request.document())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Já existe usuário com esse documento.");
                });

        // 2) Regra: não permitir e-mail duplicado
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Já existe usuário com esse e-mail.");
                });

        // 3) Define saldo inicial: se vier null, começa com 0
        BigDecimal initialBalance = request.initialBalance() != null
                ? request.initialBalance()
                : BigDecimal.ZERO;

        // 4) Monta a entidade User usando o Builder
        User user = User.builder()
                .fullName(request.fullName())
                .document(request.document())
                .email(request.email())
                .balance(initialBalance)
                .createdAt(LocalDateTime.now())
                .build();

        // 5) Salva no banco
        User saved = userRepository.save(user);

        // 6) Monta o DTO de resposta
        return new UserResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getDocument(),
                saved.getEmail(),
                saved.getBalance(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        // 1) Busca o usuário no banco
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // 2) Converte a entidade para DTO de resposta
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getDocument(),
                user.getEmail(),
                user.getBalance(),
                user.getCreatedAt()
        );
    }
}
