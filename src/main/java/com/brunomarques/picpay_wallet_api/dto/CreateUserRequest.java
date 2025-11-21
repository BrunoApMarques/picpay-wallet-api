package com.brunomarques.picpay_wallet_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateUserRequest(

        @NotBlank(message = "Nome completo é obrigatório")
        String fullName,

        @NotBlank(message = "Documento é obrigatório")
        String document,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @PositiveOrZero(message = "Saldo inicial não pode ser negativo")
        BigDecimal initialBalance
)
{

}