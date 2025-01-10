package com.shopping.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 多個 CartItem 對應一個 Cart
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne // 每個 CartItem 對應一個 Product
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // 該商品的購買數量
}
