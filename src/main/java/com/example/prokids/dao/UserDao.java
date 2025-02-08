package com.example.prokids.dao;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDao {
    private String id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
