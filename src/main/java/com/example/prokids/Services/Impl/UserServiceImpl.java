package com.example.prokids.Services.Impl;


import com.example.prokids.Model.User;
import com.example.prokids.Services.UserService;
import com.example.prokids.repositories.RoleRepository;
import com.example.prokids.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class UserServiceImpl implements UserService {
    private final String USER_ROLE_ID = "1";
    private final String ADMIN_ROLE_ID = "2";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    @Override
    public User saveUser(User myUser) {
        if (!userRepository.findByLogin(myUser.getLogin()).isPresent()) {
            myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
            myUser.setRoles(List.of(roleRepository.findById(USER_ROLE_ID).get()));
            return userRepository.save(myUser);
        }else {
            return null;
        }
    }

}
