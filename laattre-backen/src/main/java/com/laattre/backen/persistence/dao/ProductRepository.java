package com.laattre.backen.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
