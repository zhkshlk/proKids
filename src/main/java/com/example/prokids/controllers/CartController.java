package com.example.prokids.controllers;

import com.example.prokids.Model.Cart;
import com.example.prokids.Services.CartService;
import com.example.prokids.dto.CartItemCreate;
import com.example.prokids.dto.CartItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CartItemResponse>> get(Pageable pageable) {
        Page<Cart> cartItems = cartService.get(pageable);

        Page<CartItemResponse> cartItemsResponse = cartItems.map(CartItemResponse::new);
        return ResponseEntity.ok(cartItemsResponse);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> add(@RequestBody CartItemCreate cartItemDto) {
        Cart cart = cartService.add(cartItemDto.getProductId(), cartItemDto.getQuantity());
        return ResponseEntity.ok(new CartItemResponse(cart));
    }

    @PutMapping
    public ResponseEntity<CartItemResponse> update(@RequestBody CartItemCreate cartItemDto) {
        Cart cart = cartService.add(cartItemDto.getProductId(), cartItemDto.getQuantity());
        return ResponseEntity.ok(new CartItemResponse(cart));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable String productId) {
        cartService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
