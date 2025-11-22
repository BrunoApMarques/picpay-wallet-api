package com.brunomarques.picpay_wallet_api.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(Long userId, BigDecimal amount) {
        super("Usuário " + userId + " não possui saldo suficiente para transferir " + amount);
    }
}

