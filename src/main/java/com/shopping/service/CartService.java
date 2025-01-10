package com.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.CartItemRepository;
import com.shopping.dao.CartRepository;
import com.shopping.model.entity.Cart;
import com.shopping.model.entity.CartItem;
import com.shopping.model.entity.Product;
import com.shopping.model.entity.User;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	
	//秀出購物車
	public List<CartItem> getCartItems(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("購物車不存在"));
        return cartItemRepository.findByCart(cart);
    }
	
	//新增商品至購物車
//	public void addProductToCart(User user, Product product, int quantity) {
//		
//        Cart cart = cartRepository.findByUser(user);
//        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(cart, product);
//
//        if (cartItemOptional.isPresent()) {
//            CartItem cartItem = cartItemOptional.get();
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//            cartItemRepository.save(cartItem);
//        } else {
//            CartItem newItem = new CartItem();
//            newItem.setCartId(cart);
//            newItem.setProductId(product);
//            newItem.setQuantity(quantity);
//            cartItemRepository.save(newItem);
//        }
//    } 
	
	//從購物車移除商品
//    public void removeProductFromCart(Long userId, Long productId) {
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("購物車不存在"));
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("商品不存在"));
//
//        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
//                .orElseThrow(() -> new RuntimeException("購物車中無此商品"));
//
//        cartItemRepository.delete(cartItem);
//    }
}
