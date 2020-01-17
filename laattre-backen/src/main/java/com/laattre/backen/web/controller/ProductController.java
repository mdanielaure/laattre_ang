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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.security.ISecurityUserService;
import com.laattre.backen.service.CategoryService;
import com.laattre.backen.service.ProductService;
import com.laattre.backen.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ISecurityUserService securityUserService; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private CategoryService categoryService;
	
	List<User> listUsers;
	List<Category> categories;
	List<Category> subCategories;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		listUsers = userService.findAll();
		model.addAttribute("users", listUsers);
		
		categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
	    	
	    	final User createdBy = securityUserService.getCurrentUser();

	     	
	   	product.setCreateDate(new Date());
	   	product.setCreatedBy(createdBy);
		productService.save(product);

		MultipartFile productImage = product.getProductImage();

		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId() + ".png";
			String fileLocation = new File("resources\\static\\image\\product").getAbsolutePath() + "\\" + name;
			System.out.println("fileLocation " + request.getSession().getServletContext().getRealPath("/WEB-INF/"));
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/") + "\\resources\\static\\image\\product\\" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:productList";
	}
	
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
	
	@RequestMapping("/productList")
	public String productList(Model model) {
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);		
		return "productList";
		
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

	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		productService.removeOne(Long.parseLong(id.substring(11)));
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "redirect:/product/productList";
	}

}
