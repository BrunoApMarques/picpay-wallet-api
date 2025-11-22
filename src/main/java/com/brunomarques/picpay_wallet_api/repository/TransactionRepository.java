package com.brunomarques.picpay_wallet_api.repository;

import com.brunomarques.picpay_wallet_api.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
