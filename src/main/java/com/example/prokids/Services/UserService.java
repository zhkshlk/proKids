package com.example.prokids.Services;

import com.example.prokids.Model.User;
import com.example.prokids.dto.UserUpdate;

import java.util.List;

public interface UserService {
    User findById(String id);
    User saveUser(User user);
    void deleteUserById(String id);
    List<User> getAllUsers();
    User getByUsername(String username);
    User getMyAccount();

    User getCurrentUser();

    User updateMyAccount(UserUpdate request);
}
