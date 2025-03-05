package com.example.prokids.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Связь с пользователем

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Связь с продуктом

    @Column(name = "quantity", nullable = false)
    private int quantity;  // Количество товара

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Order(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.status = OrderStatus.IN_PROGRESS;
    }
}
