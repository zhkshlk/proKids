package com.example.prokids.controllers;

import com.example.prokids.Model.Order;
import com.example.prokids.Model.OrderStatus;
import com.example.prokids.Services.OrderService;
import com.example.prokids.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Заказы")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> get(Pageable pageable) {
        Page<Order> orderItems = orderService.get(pageable);

        Page<OrderResponse> orderResponse = orderItems.map(OrderResponse::new);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> add(@RequestBody OrderCreate orderCreate) {
        Order order = orderService.add(orderCreate.getProductId(), orderCreate.getQuantity());
        return ResponseEntity.ok(new OrderResponse(order));
    }

    @PutMapping
    public ResponseEntity<OrderResponse> update(@RequestBody OrderRequestUpdate orderUpdate) {
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(orderUpdate.status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
        Order order = orderService.update(orderUpdate.getId(), newStatus);
        return ResponseEntity.ok(new OrderResponse(order));
    }
}
