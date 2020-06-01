package com.laattre.backen.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;

public interface ProductService {
	
	Product save(Product product);

	List<Product> findAll();
	
	List<Product> findByCategories(Set<Category> category);
	
	Optional<Product> findOne(Long id);
	
	void removeOne(Long id);
	
	Page<Product> findPaginated(Pageable pageable, Set<Category> category);
}
