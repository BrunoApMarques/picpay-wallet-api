package com.brunomarques.picpay_wallet_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long payerId,
        Long payeeId,
        BigDecimal amount,
        LocalDateTime createdAt
) { }
