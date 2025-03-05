package com.example.prokids.repositories;

import com.example.prokids.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(String userId, Pageable pageable);
    Page<Order> findAllByOrderByUserIdAsc(Pageable pageable);
}
