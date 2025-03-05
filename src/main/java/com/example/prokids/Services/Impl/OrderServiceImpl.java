package com.example.prokids.Services.Impl;

import com.example.prokids.Model.*;
import com.example.prokids.Services.OrderService;
import com.example.prokids.Services.UserService;
import com.example.prokids.repositories.OrderRepository;
import com.example.prokids.repositories.ProductRepository;
import com.example.prokids.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Page<Order> get(Pageable pageable) {
        User user = userService.getCurrentUser();
        return orderRepository.findByUserId(user.getId(), pageable);
    }

    @Override
    public Order add(String productId, int quantity) {
        User user = userService.getCurrentUser();
        Optional<User> userOptional = userRepository.findById(user.getId());
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            Product product = productOptional.get();

            Order order = new Order(user, product, quantity);;

            orderRepository.save(order);

            return order;
        }

        return null;
    }

    @Override
    public Order update(Long id, OrderStatus status) {
        User user = userService.getCurrentUser();
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (user.getRoles().stream().anyMatch(role -> role.equals("ROLE_ADMIN"))) {
                order.setStatus(status);
            } else {
                order.setStatus(OrderStatus.CANCELLED);
            }

            orderRepository.save(order);

            return order;
        }

        return null;
    }
}
