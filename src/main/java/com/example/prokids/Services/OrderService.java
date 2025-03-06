package com.example.prokids.Services;

import com.example.prokids.Model.Order;
import com.example.prokids.Model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> getAll(Pageable pageable);
    Page<Order> get(Pageable pageable);

    Order add(String productId, int quantity);
    Order update(Long id, OrderStatus status);
}
