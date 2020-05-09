package com.laattre.backen.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laattre.backen.persistence.dao.OrderRepository;
import com.laattre.backen.persistence.model.BillingAddress;
import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.Payment;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.ShippingAddress;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.service.CartItemService;
import com.laattre.backen.service.OrderService;



@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Product product = cartItem.getProduct();
			cartItem.setOrder(order);
			product.setInStockNumber(product.getInStockNumber() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Optional<Order> findOne(Long id) {
		return orderRepository.findById(id);
	}

}
