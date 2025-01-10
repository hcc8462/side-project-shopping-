package com.shopping.dao;

import com.shopping.model.entity.Cart;
import com.shopping.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
    Cart findByUser(User user);
    
}