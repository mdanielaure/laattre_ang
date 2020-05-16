package com.laattre.backen.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.service.CategoryService;

@CrossOrigin(origins= "*")
@RestController
public class CategoryController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/api/categories")
	public ResponseEntity<?> getcategories(Model model) {
	
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return ResponseEntity.ok(model);
	}

}
