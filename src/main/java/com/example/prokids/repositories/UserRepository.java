package com.example.prokids.repositories;

import com.example.prokids.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getByUsername (String username);

    Optional<User> getByEmail(String email);
}