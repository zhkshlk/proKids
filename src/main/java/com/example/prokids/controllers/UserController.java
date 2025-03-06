package com.example.prokids.controllers;

import com.example.prokids.Model.User;
import com.example.prokids.Services.UserService;
import com.example.prokids.dto.ProductCreate;
import com.example.prokids.dto.UserResponse;
import com.example.prokids.dto.UserUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользовател")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Иноформация об аккаунте")
    @GetMapping
    public ResponseEntity<?> getMyAccount() {
        User user = userService.getMyAccount();
        UserResponse userResponse = new UserResponse(user);
        return ResponseEntity.ok(userResponse);
    }


    @Operation(summary = "Иноформация об аккаунте")
    @PostMapping
    public ResponseEntity<?> updateMyAccount(@RequestBody UserUpdate request) {
        User user = userService.updateMyAccount(request);
        UserResponse userResponse = new UserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
