package com.laattre.backen.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.security.ISecurityUserService;
import com.laattre.backen.service.CategoryService;
import com.laattre.backen.service.ProductService;
import com.laattre.backen.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("product/")
public class ProductController {

	@Autowired
	private ProductService productService;
	

	
	
	@RequestMapping("/productInfo")
	public String productInfo(@RequestParam("id") Long id, Model model) {
		Product product = productService.findOne(id).get();
		model.addAttribute("product", product);
		
		return "productInfo";
	}
	
	@RequestMapping("/updateProduct")
	public String updateProduct(@RequestParam("id") Long id, Model model) {
		Optional<Product> product = productService.findOne(id);
		model.addAttribute("product", product);
		
		return "updateProduct";
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateProductPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
		productService.save(product);
		
		MultipartFile productImage = product.getProductImage();
		
		if(!productImage.isEmpty()) {
			try {
				byte[] bytes = productImage.getBytes();
				String name = product.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/product/"+name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/product/productInfo?id="+product.getId();
	}
	
	
	@RequestMapping("/shop")
	public String productShop(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
	    final int currentPage = page.orElse(1);
	    final int pageSize = size.orElse(5);
	    
	    Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
	    
	    model.addAttribute("productPage", productPage);
	    
	    int totalPages = productPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	    return "shop";
		
	}
	
	@RequestMapping("/productList")
	public List<Product> productList(Model model) {
		List<Product> productList = productService.findAll();
		//model.addAttribute("productList", productList);		
		return productList;
		
	}

}
