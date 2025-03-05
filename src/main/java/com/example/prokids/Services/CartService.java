package com.example.prokids.Services;

import com.example.prokids.Model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    Page<Cart> get(Pageable pageable);

    Cart add(String productId, int quantity);
    Cart update(String productId, int quantity);
    void delete(String productId);
}
