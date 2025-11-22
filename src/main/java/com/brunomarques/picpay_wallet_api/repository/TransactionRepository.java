package com.brunomarques.picpay_wallet_api.repository;

import com.brunomarques.picpay_wallet_api.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca todas as transações em que o usuário participou
    List<Transaction> findByPayer_IdOrPayee_Id(Long payerId, Long payeeId);
}
