package com.laattre.backen.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laattre.backen.persistence.dao.CartItemRepository;
import com.laattre.backen.persistence.dao.ProductToCartItemRepository;
import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.Product;
import com.laattre.backen.persistence.model.ProductToCartItem;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.service.CartItemService;


@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductToCartItemRepository productToCartItemRepository;
	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}
	
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getProduct().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}
	
	public CartItem addProductToCartItem(Product product, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		if(cartItemList!=null) {
			for (CartItem cartItem : cartItemList) {
				if(product.getId() == cartItem.getProduct().getId()) {
					cartItem.setQty(cartItem.getQty()+qty);
					cartItem.setSubtotal(new BigDecimal(product.getOurPrice()).multiply(new BigDecimal(cartItem.getQty()+qty)));
					cartItemRepository.save(cartItem);
					return cartItem;
				}
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(product.getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		ProductToCartItem productToCartItem = new ProductToCartItem();
		productToCartItem.setProduct(product);
		productToCartItem.setCartItem(cartItem);
		productToCartItemRepository.save(productToCartItem);
		
		return cartItem;
	}
	
	public Optional<CartItem> findById(Long id) {
		return cartItemRepository.findById(id);
	}
	
	public void removeCartItem(CartItem cartItem) {
		productToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}
	
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	public List<CartItem> findByOrder(Order order) {
		return cartItemRepository.findByOrder(order);
	}

}
