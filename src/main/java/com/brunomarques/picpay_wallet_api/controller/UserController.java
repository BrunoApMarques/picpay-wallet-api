package com.brunomarques.picpay_wallet_api.controller;

import com.brunomarques.picpay_wallet_api.dto.CreateUserRequest;
import com.brunomarques.picpay_wallet_api.dto.TransactionResponse;
import com.brunomarques.picpay_wallet_api.dto.UserResponse;
import com.brunomarques.picpay_wallet_api.service.TransactionService;
import com.brunomarques.picpay_wallet_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // todas as rotas aqui começam com /users
public class UserController {

    private final UserService userService;

    private final TransactionService transactionService;


    public UserController(UserService userService,
                          TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response); // HTTP 200
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(@PathVariable Long id) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByUser(id);
        return ResponseEntity.ok(transactions);
    }

}
