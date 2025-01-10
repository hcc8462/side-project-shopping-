package com.shopping.dao;

import com.shopping.model.entity.CartItem;
import com.shopping.model.entity.Cart;
import com.shopping.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    
    List<CartItem> findByCart(Cart cart);
    
}