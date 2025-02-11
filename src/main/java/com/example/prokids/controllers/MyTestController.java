package com.example.prokids.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Test Controller", description = "Для теста")
@RestController
@Controller
@RequestMapping("/api/test")
public class MyTestController {

    @Operation(summary = "permitAll")
    @GetMapping("/permitAll")
    public ResponseEntity<?> permitAll() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User")
    @GetMapping("/user")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User Role")
    @GetMapping("/userRole")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> userRole() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin Role")
    @GetMapping("/adminRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminRole() {
        return ResponseEntity.ok().build();
    }
}

