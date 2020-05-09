package com.laattre.backen.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.laattre.backen.persistence.model.Category;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.security.ISecurityUserService;
import com.laattre.backen.service.CategoryService;
import com.laattre.backen.service.ProductService;
import com.laattre.backen.service.UserService;

@Controller
@RequestMapping("admin/")
public class AdminController {
	
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
	
	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		listUsers = userService.findAll();
		model.addAttribute("users", listUsers);
		
		categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "addProduct";
	}
	
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/product/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		productService.removeOne(Long.parseLong(id.substring(11)));
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "redirect:/product/productList";
	}
	
	
	@RequestMapping("/product/productList")
	public String productList(Model model) {
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);		
		return "productList";
		
	}

}
