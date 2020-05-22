package com.laattre.backen.service;

import java.util.List;
import java.util.Optional;

import com.laattre.backen.persistence.model.Category;

public interface CategoryService {
	
    Category save(Category product);

    List<Category> findAll();
	
    Optional<Category> findOne(Long id);
    
    List<Category> findByMenu(String menu);
	
    void removeOne(Long id);
}
