package com.brunomarques.picpay_wallet_api.service;

import com.brunomarques.picpay_wallet_api.domain.Transaction;
import com.brunomarques.picpay_wallet_api.domain.User;
import com.brunomarques.picpay_wallet_api.dto.CreateTransactionRequest;
import com.brunomarques.picpay_wallet_api.dto.TransactionResponse;
import com.brunomarques.picpay_wallet_api.exception.InsufficientBalanceException;
import com.brunomarques.picpay_wallet_api.exception.UserNotFoundException;
import com.brunomarques.picpay_wallet_api.repository.TransactionRepository;
import com.brunomarques.picpay_wallet_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository,
                              TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        if (request.payerId().equals(request.payeeId())) {
            throw new IllegalArgumentException("Pagador e recebedor não podem ser o mesmo usuário.");
        }

        // Busca usuários
        User payer = userRepository.findById(request.payerId())
                .orElseThrow(() -> new UserNotFoundException(request.payerId()));

        User payee = userRepository.findById(request.payeeId())
                .orElseThrow(() -> new UserNotFoundException(request.payeeId()));

        // Verifica saldo suficiente
        BigDecimal amount = request.amount();
        if (payer.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(payer.getId(), amount);
        }

        // Debita do pagador
        payer.setBalance(payer.getBalance().subtract(amount));

        // Credita no recebedor
        payee.setBalance(payee.getBalance().add(amount));

        // Persiste a alteração de saldo
        userRepository.save(payer);
        userRepository.save(payee);

        // Registra a transação
        Transaction transaction = Transaction.builder()
                .payer(payer)
                .payee(payee)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // Monta resposta
        return new TransactionResponse(
                saved.getId(),
                saved.getPayer().getId(),
                saved.getPayee().getId(),
                saved.getAmount(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByUser(Long userId) {

        // 1) Garante que o usuário existe
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new UserNotFoundException(userId);
        }

        // 2) Busca todas as transações onde ele é payer ou payee
        var transactions = transactionRepository.findByPayer_IdOrPayee_Id(userId, userId);

        // 3) Converte Transaction -> TransactionResponse
        return transactions.stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getPayer().getId(),
                        t.getPayee().getId(),
                        t.getAmount(),
                        t.getCreatedAt()
                ))
                .toList();
    }
}

