package com.example.prokids.services;

import com.example.prokids.model.User;
import com.example.prokids.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser (String username, String password){
        if (userRepository.findByUsername(username) == null){
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("USER");
            userRepository.save(user);
        }
        else {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
    }

    public UserDetails loadUserByUserName(String username){
        return (UserDetails) userRepository.findByUsername(username);
    }
}