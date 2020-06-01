package com.laattre.backen.persistence.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	List<Product> findAllByCategoriesIn(Set<Category> category);

}
