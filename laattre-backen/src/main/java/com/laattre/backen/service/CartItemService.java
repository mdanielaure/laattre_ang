package com.laattre.backen.service;

import java.util.List;
import java.util.Optional;

import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;


public interface CartItemService {
List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addProductToCartItem(Product produc, User user, int qty);
	
	Optional<CartItem> findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	List<CartItem> findByOrder(Order order);

}
