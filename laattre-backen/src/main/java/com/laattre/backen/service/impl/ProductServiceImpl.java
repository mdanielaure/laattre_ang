package com.laattre.backen.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.laattre.backen.service.ProductService;
import com.laattre.backen.persistence.dao.ProductRepository;
import com.laattre.backen.persistence.model.Product;



@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}
	
	public Optional<Product> findOne(Long id) {
		return productRepository.findById(id);
	}
	
	public void removeOne(Long id) {
		productRepository.deleteById(id);
	}
	
	public Page<Product> findPaginated(Pageable pageable) {
	    List<Product> products = findAll();    
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;
	    List<Product> list;

	    if (products.size() < startItem) {
	        list = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, products.size());
	        list = products.subList(startItem, toIndex);
	    }

	    Page<Product> bookPage = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());

	    return bookPage;

	    }
}
