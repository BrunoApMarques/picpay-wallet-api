package com.brunomarques.picpay_wallet_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // nome da tabela no banco
@Data // Lombok: gera getter, setter, equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificador único do usuário

    @Column(nullable = false)
    private String fullName; // nome completo do usuário

    @Column(nullable = false, unique = true)
    private String document; // CPF ou documento único

    @Column(nullable = false, unique = true)
    private String email; // e-mail único

    @Column(nullable = false)
    private BigDecimal balance; // saldo da carteira

    @Column(nullable = false)
    private LocalDateTime createdAt; // data/hora de criação
}