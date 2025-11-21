package com.brunomarques.picpay_wallet_api.controller;

import com.brunomarques.picpay_wallet_api.dto.CreateUserRequest;
import com.brunomarques.picpay_wallet_api.dto.UserResponse;
import com.brunomarques.picpay_wallet_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // todas as rotas aqui começam com /users
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
