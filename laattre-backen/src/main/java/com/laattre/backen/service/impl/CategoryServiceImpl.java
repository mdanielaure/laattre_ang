package com.laattre.backen.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laattre.backen.service.CategoryService;
import com.laattre.backen.persistence.dao.CategoryRepository;
import com.laattre.backen.persistence.model.Category;



@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> findAll() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	public Optional<Category> findOne(Long id) {
		return categoryRepository.findById(id);
	}
	
	public void removeOne(Long id) {
		categoryRepository.deleteById(id);
	}

	public List<Category> findByMenu(String menu) {
		return categoryRepository.findByMenu(menu);
	}
}
