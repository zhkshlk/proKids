package com.example.prokids.controllers;

import com.example.prokids.Services.Impl.AuthenticationServiceImpl;
import com.example.prokids.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Operation(summary = "Обновить токен доступа")
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody @Valid RefreshRequest request
    ) {
        try {
            RefreshResponse response = authenticationService.refresh(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Выйти из системы")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody @Valid LogoutRequest request
    ) {
        try {
            authenticationService.logout(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Токен не найден");
        }
    }
}

