package com.laattre.backen.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.service.CategoryService;
import com.laattre.backen.service.ProductService;
import com.laattre.backen.service.UserService;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/product")
public class ProductController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping("/productInfo")
	public ResponseEntity<?>  productInfo(@RequestParam("id") Long id, Model model) {
		Product product = productService.findOne(id).get();
		model.addAttribute("product", product);
		
		return ResponseEntity.ok(model
				//.stream()
		        //.filter(this::isCool)
		       // .collect(Collectors.toList())
				);
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
	    
	    Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), null);
	    
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
	
	@GetMapping("/productList")
	public  ResponseEntity<?> productList(Model model, 
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam("categoryId") Optional<Long> categoryId,
			@RequestParam("menu") Optional<String> menu
			) {
		
		//model.addAttribute("productList", productList);
		 final int currentPage = page.orElse(1);
		 final int pageSize = size.orElse(5);
		 final Long catId = categoryId.orElse(null);
		 String menuQuerry = menu.orElse(null);
		 Page<Product> productPage = null;
		 
		 
		 if(catId !=null && catId > 0) {
			 Category category = categoryService.findOne(catId).orElse(null);
			 Set<Category> categories = new HashSet<>();
			 
			 if (category != null && category.getParent() != null) {
				 categories.add(category);
			 }
			 else if (category != null && category.getParent() == null) {
				 categories.addAll(category.getChildCategories());
			 }
			 
			 productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), categories);
		 }
		 else if (menuQuerry != null && !menuQuerry.equals("0")) {
			List<Category> category = categoryService.findByMenu(menuQuerry);
			Set<Category> categories = new HashSet<>();
			 
			for(int i=0; i< category.size(); i++) {
				 if (category.get(i) != null && category.get(i).getParent() != null) {
					 categories.add(category.get(i));
				 }
				 else if (category.get(i) != null && category.get(i).getParent() == null) {
					 categories.addAll(category.get(i).getChildCategories());
				 }
			}
			
			 productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), categories);
		 }
		 else {
			 productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), null);
		 }
		 
		 
		 model.addAttribute("productPage", productPage);
		 int totalPages = productPage.getTotalPages();
		 if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
		 
		 return ResponseEntity.ok(model
				//.stream()
		        //.filter(this::isCool)
		       // .collect(Collectors.toList())
				);
		
	}
	
}
