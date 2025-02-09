package com.example.prokids.dto;

import com.example.prokids.Model.Role;
import com.example.prokids.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;

    private String email;

    private String role;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(", "));
    }
}