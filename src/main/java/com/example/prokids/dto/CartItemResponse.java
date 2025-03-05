package com.example.prokids.dto;

import com.example.prokids.Model.Cart;
import com.example.prokids.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private ProductResponse product;
    private int quantity;

    public CartItemResponse(Cart cart) {
        this.id = cart.getId();
        this.product = new ProductResponse(cart.getProduct());
        this.quantity = cart.getQuantity();
    }
}
