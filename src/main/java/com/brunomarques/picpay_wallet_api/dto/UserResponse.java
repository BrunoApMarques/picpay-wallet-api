package com.brunomarques.picpay_wallet_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String document,
        String email,
        BigDecimal balance,
        LocalDateTime createdAt
)
{

}
