package com.laattre.backen.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.laattre.backen.persistence.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

}
