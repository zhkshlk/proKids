package com.example.prokids.controllers;

import com.example.prokids.Services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Data
public class UserController {
    @Autowired
    private final UserService userService;
}
