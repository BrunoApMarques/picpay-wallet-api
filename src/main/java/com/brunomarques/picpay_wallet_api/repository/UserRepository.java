package com.brunomarques.picpay_wallet_api.repository;

import com.brunomarques.picpay_wallet_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);

    Optional<User> findByEmail(String email);
}