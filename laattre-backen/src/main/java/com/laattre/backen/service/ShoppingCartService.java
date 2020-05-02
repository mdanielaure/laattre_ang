package com.laattre.backen.service;


import com.laattre.backen.persistence.model.ShoppingCart;


public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	
	void clearShoppingCart(ShoppingCart shoppingCart);
	
	ShoppingCart createUserCart(ShoppingCart shoppingCart);
	
}
