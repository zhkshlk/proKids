package com.example.prokids.controllers;

import com.example.prokids.Services.Impl.AuthenticationServiceImpl;
import com.example.prokids.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "Управление аутентификацией и регистрацией")
@RestController
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @Operation(summary = "Регистрация")
    @PostMapping("/registration")
    public ResponseEntity<LoginResponse> registration(
            @RequestBody @Valid UserCreate request
    ) {
        LoginResponse response = authenticationService.registration(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Авторизация")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {

        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Авторизация"
    )
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody @Valid RefreshRequest request
    ) {

        RefreshResponse response = authenticationService.refresh(request);
        return ResponseEntity.ok(response);
    }
}

