package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Role;
import com.example.prokids.Model.User;
import com.example.prokids.Services.AuthenticationService;
import com.example.prokids.dto.*;
import com.example.prokids.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public LoginResponse registration(UserCreate request) {
        Role userRole = (Role) roleRepository.findByRoleName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(userRole))
                .enabled(true)
                .build();

        userService.saveUser(user);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse response = new LoginResponse(accessToken, refreshToken);
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка аутентификации: " + e.getMessage());
        }

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse response = new LoginResponse(accessToken, refreshToken);
        return response;
    }

    @Override
    public RefreshResponse refresh(RefreshRequest request) {
        String username = jwtService.extractUserName(request.getRefreshToken());
        User user = userService.getByUsername(username);
        if (jwtService.isTokenValid(request.getRefreshToken(), user)) {
            var accessToken = jwtService.generateAccessToken(user);
            RefreshResponse response = new RefreshResponse();
            response.setAccessToken(accessToken);
            return response;
        } else {
            throw new AccessDeniedException("Доступ запрещен");
        }
    }
}
