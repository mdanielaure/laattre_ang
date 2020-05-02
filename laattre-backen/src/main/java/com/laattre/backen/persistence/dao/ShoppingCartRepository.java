package com.laattre.backen.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.ShoppingCart;


public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
