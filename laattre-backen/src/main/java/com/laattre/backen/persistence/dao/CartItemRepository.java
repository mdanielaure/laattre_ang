package com.laattre.backen.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.ShoppingCart;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	List<CartItem> findByOrder(Order order);
}
