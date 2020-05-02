package com.laattre.backen.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.ProductToCartItem;

@Transactional
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
