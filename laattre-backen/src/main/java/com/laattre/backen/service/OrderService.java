package com.laattre.backen.service;

import java.util.Optional;

import com.laattre.backen.persistence.model.BillingAddress;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.Payment;
import com.laattre.backen.persistence.model.ShippingAddress;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user);
	
	Optional<Order> findOne(Long id);
}
