package com.brunomarques.picpay_wallet_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransactionRequest(

        @NotNull(message = "ID do pagador é obrigatório")
        Long payerId,

        @NotNull(message = "ID do recebedor é obrigatório")
        Long payeeId,

        @NotNull(message = "Valor da transferência é obrigatório")
        @Positive(message = "Valor da transferência deve ser maior que zero")
        BigDecimal amount
) { }
