package com.laattre.backen.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.service.CartItemService;
import com.laattre.backen.service.ProductService;
import com.laattre.backen.service.ShoppingCartService;
import com.laattre.backen.service.UserService;


@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/cart")
	public ResponseEntity<?> shoppingCart(Model model, @RequestParam("username") String username) {
		User user = userService.findUserByEmail(username);
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		System.out.println("cart " + shoppingCart.getId());
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		
		
		if (cartItemList.size() == 0) {
			model.addAttribute("emptyCart", true);
		}
		else {
			shoppingCartService.updateShoppingCart(shoppingCart);
			model.addAttribute("emptyCart", false);
		}
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		
		return ResponseEntity.ok(model);
	}

	@PostMapping("/addItem")
	public ResponseEntity<?> addItem(
			Model model,
			@RequestParam("pId") Long pId,
			@RequestParam("qty") int qty,
			@RequestParam("username") String username
			) {
		User user = userService.findUserByEmail(username);
		Product product = productService.findOne(pId).orElse(null);
		model.addAttribute("pId", product.getId());
		if (qty > product.getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);
			return ResponseEntity.ok(model);
			//return "forward:/productDetail?id="+product.getId();
		}
		
		
		if(user.getShoppingCart()==null) {
			ShoppingCart cart = new ShoppingCart();
			cart.setUser(user);
			cart = shoppingCartService.createUserCart(cart);
			
		}
		
		CartItem cartItem = cartItemService.addProductToCartItem(product, user, qty);
		model.addAttribute("addProductSuccess", true);
		
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("/updateCartItem")
	public ResponseEntity<?> updateShoppingCart(
			Model model,
			@RequestParam("id") Long cartItemId,
			@RequestParam("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId).orElse(null);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);
		
		model.addAttribute("updateCartSuccess", true);
		
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("/removeItem")
	public ResponseEntity<?> removeItem(Model model, @RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id).orElse(null));
		model.addAttribute("deleteFromCartSuccess", true);
		return ResponseEntity.ok(model);
	}

}
