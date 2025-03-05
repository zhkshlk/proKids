package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Cart;
import com.example.prokids.Model.Product;
import com.example.prokids.Model.User;
import com.example.prokids.Services.CartService;
import com.example.prokids.Services.UserService;
import com.example.prokids.repositories.CartRepository;
import com.example.prokids.repositories.ProductRepository;
import com.example.prokids.repositories.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Page<Cart> get(Pageable pageable) {
        User user = userService.getCurrentUser();
        return cartRepository.findByUserId(user.getId(), pageable);
    }

    @Override
    public Cart add(String productId, int quantity) {
        return getCartResponse(productId, quantity);
    }

    @Override
    public Cart update(String productId, int quantity) {
        return getCartResponse(productId, quantity);
    }

    @Nullable
    private Cart getCartResponse(String productId, int quantity) {
        User user = userService.getCurrentUser();
        Optional<User> userOptional = userRepository.findById(user.getId());
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            Product product = productOptional.get();

            Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(user.getId(), productId);
            Cart cart;
            if (cartOptional.isPresent()) {
                cart = cartOptional.get();
                cart.setQuantity(quantity <= 0 ? 1 : quantity);
            } else {
                cart = new Cart(user, product, quantity);
            }

            cartRepository.save(cart);

            return cart;
        }

        return null;
    }

    @Override
    public void delete(String productId) {
        User user = userService.getCurrentUser();
        Optional<User> userOptional = userRepository.findById(user.getId());
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(user.getId(), productId);
        if (userOptional.isPresent() && cartOptional.isPresent()) {
            cartRepository.deleteById(cartOptional.get().getId());
        } else {
            throw new EntityNotFoundException();
        }
    }
}
