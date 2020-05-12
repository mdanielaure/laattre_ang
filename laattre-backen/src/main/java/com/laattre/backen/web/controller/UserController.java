package com.laattre.backen.web.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.persistence.model.UserShipping;
import com.laattre.backen.security.ActiveUserStore;
import com.laattre.backen.service.CartItemService;
import com.laattre.backen.service.OrderService;
import com.laattre.backen.service.UserService;

@CrossOrigin(origins= "*")
@RestController
public class UserController {

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    UserService userService;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    CartItemService cartItemService;

    @RequestMapping(value = "/loggedUsers", method = RequestMethod.GET)
    public String getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }

    @RequestMapping(value = "/loggedUsersFromSessionRegistry", method = RequestMethod.GET)
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }
    
    
    @GetMapping("/user/orderDetail")
	public ResponseEntity<?> orderDetail(
			@RequestParam("id") Long orderId,
			@RequestParam("userEmail") String userEmail, 
			Model model
			){
		User user = userService.findUserByEmail(userEmail);
		Order order = orderService.findOne(orderId).orElse(null);
		
		if(order.getUser().getId()!=user.getId()) {
			//return "badRequestPage";
			model.addAttribute("badRequest", true);
			return ResponseEntity.ok(model);
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			//model.addAttribute("user", user);
			model.addAttribute("order", order);
			
			//model.addAttribute("userPaymentList", user.getUserPaymentList());
			//model.addAttribute("userShippingList", user.getUserShippingList());
			//model.addAttribute("orderList", user.getOrderList());
			
			UserShipping userShipping = new UserShipping();
			//model.addAttribute("userShipping", userShipping);
			
			//List<String> stateList = USConstants.listOfUSStatesCode;
			//Collections.sort(stateList);
			//model.addAttribute("stateList", stateList);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);
			
			//return "myProfile";
			return ResponseEntity.ok(model);
		}
	}
	
}
