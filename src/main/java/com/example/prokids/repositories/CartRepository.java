package com.example.prokids.repositories;

import com.example.prokids.Model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Метод для нахождения корзины пользователя
    Page<Cart> findByUserId(String userId, Pageable pageable);
    // Метод для нахождения корзины по пользователю и продукту
    Optional<Cart> findByUserIdAndProductId(String userId, String productId);
}
