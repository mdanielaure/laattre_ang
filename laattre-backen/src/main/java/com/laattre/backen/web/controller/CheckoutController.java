package com.laattre.backen.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laattre.backen.persistence.model.BillingAddress;
import com.laattre.backen.persistence.model.CartItem;
import com.laattre.backen.persistence.model.Order;
import com.laattre.backen.persistence.model.Payment;
import com.laattre.backen.persistence.model.ShippingAddress;
import com.laattre.backen.persistence.model.ShoppingCart;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.persistence.model.UserBilling;
import com.laattre.backen.persistence.model.UserPayment;
import com.laattre.backen.persistence.model.UserShipping;
import com.laattre.backen.service.BillingAddressService;
import com.laattre.backen.service.CartItemService;
import com.laattre.backen.service.OrderService;
import com.laattre.backen.service.PaymentService;
import com.laattre.backen.service.ShippingAddressService;
import com.laattre.backen.service.ShoppingCartService;
import com.laattre.backen.service.UserPaymentService;
import com.laattre.backen.service.UserService;
import com.laattre.backen.service.UserShippingService;
import com.laattre.backen.web.dto.CheckoutDto;
import com.laattre.backen.web.util.MailConstructor;


@CrossOrigin(origins= "*")
@RestController
public class CheckoutController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private ShippingAddress shippingAddress = new ShippingAddress();
	private BillingAddress billingAddress = new BillingAddress();
	private Payment payment = new Payment();

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserShippingService userShippingService;

	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/checkout")
	public ResponseEntity<?> checkout(@RequestParam("cartId") Long cartId,
			Model model,
			@RequestParam("username") String username) {
		User user = userService.findUserByEmail(username);

		if (cartId != user.getShoppingCart().getId()) {
			//return "badRequestPage";
			model.addAttribute("error", "badRequestPage");
			return ResponseEntity.ok(model);
		}

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

		if (cartItemList.size() == 0) {
			model.addAttribute("emptyCart", true);
			//return "forward:/shoppintCart/cart";
			return ResponseEntity.ok(model);
		}

		for (CartItem cartItem : cartItemList) {
			if (cartItem.getProduct().getInStockNumber() < cartItem.getQty()) {
				model.addAttribute("notEnoughStock", true);
				//return "forward:/shoppingCart/cart";
				return ResponseEntity.ok(model);
			}
		}

		List<UserShipping> userShippingList = user.getUserShippingList();
		List<UserPayment> userPaymentList = user.getUserPaymentList();

		model.addAttribute("userShippingList", userShippingList);
		model.addAttribute("userPaymentList", userPaymentList);

		if (userPaymentList.size() == 0) {
			model.addAttribute("emptyPaymentList", true);
		} else {
			model.addAttribute("emptyPaymentList", false);
		}

		if (userShippingList.size() == 0) {
			model.addAttribute("emptyShippingList", true);
		} else {
			model.addAttribute("emptyShippingList", false);
		}

		ShoppingCart shoppingCart = user.getShoppingCart();

		for (UserShipping userShipping : userShippingList) {
			if (userShipping.isUserShippingDefault()) {
				shippingAddressService.setByUserShipping(userShipping, shippingAddress);
			}
		}

		for (UserPayment userPayment : userPaymentList) {
			if (userPayment.isDefaultPayment()) {
				paymentService.setByUserPayment(userPayment, payment);
				billingAddressService.setByUserBilling(userPayment.getUserBilling(), billingAddress);
			}
		}

		model.addAttribute("shippingAddress", shippingAddress);
		//model.addAttribute("payment", payment);
		model.addAttribute("billingAddress", billingAddress);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);

		/*List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);*/

		model.addAttribute("classActiveShipping", true);

		/*if (missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}*/

		return ResponseEntity.ok(model);

	}
	
	
	public class MapSerializer extends JsonSerializer<SpecialMap> {
	    @Override
	    public void serialize(SpecialMap map, JsonGenerator jgen,
	                          SerializerProvider provider) throws IOException,
	            JsonProcessingException {
	        jgen.writeStartObject();
	        for (String key : map.keySet()) {
	            jgen.writeStringField(key, map.get(key));
	        }
	        jgen.writeEndObject();
	    }
	}


	public class SpecialMap extends HashMap<String,String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6743317974559229706L;
	}
	

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public ResponseEntity<?> checkoutPost(@RequestBody CheckoutDto checkoutDto 
			//Model model
			) throws IOException{
		
		//resolve SerializationFeature.FAIL_ON_EMPTY_BEANS issue
		Map<String, Object> model = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(SpecialMap.class, new MapSerializer());
        mapper.registerModule(module);


		LOGGER.debug("Checkout with folowing info {}", checkoutDto);
		
		billingAddress = checkoutDto.getBillingAddress();
		shippingAddress = checkoutDto.getShippingAddress();
		payment = checkoutDto.getPayment();
		
		ShoppingCart shoppingCart = userService.findUserByEmail(checkoutDto.getUsername()).getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		//resolve SerializationFeature.FAIL_ON_EMPTY_BEANS issue
		String serialized = mapper.writeValueAsString(cartItemList);
	       // return serialized;
		model.put("cartItemList", serialized);

		if (checkoutDto.getBillingSameAsShipping().equals("true")) {
			billingAddress.setBillingAddressFirstName(shippingAddress.getShippingAddressFirstName());
			billingAddress.setBillingAddressLastName(shippingAddress.getShippingAddressLastName());
			billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
			billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
			billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
			billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
			billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
			billingAddress.setBillingAddressZipcode(shippingAddress.getShippingAddressZipcode());
			billingAddress.setBillingAddressPhone(shippingAddress.getShippingAddressPhone());
		}

		/*if (shippingAddress.getShippingAddressStreet1().isEmpty() 
				|| shippingAddress.getShippingAddressCity().isEmpty()
				|| shippingAddress.getShippingAddressState().isEmpty()
				|| shippingAddress.getShippingAddressFirstName().isEmpty()
				|| shippingAddress.getShippingAddressLastName().isEmpty()
				|| shippingAddress.getShippingAddressZipcode().isEmpty() 
				|| shippingAddress.getShippingAddressPhone().isEmpty()
				//|| payment.getCardNumber().isEmpty()
				//|| payment.getCvc() == 0 
				|| billingAddress.getBillingAddressStreet1().isEmpty()
				|| billingAddress.getBillingAddressCity().isEmpty() 
				|| billingAddress.getBillingAddressState().isEmpty()
				|| billingAddress.getBillingAddressFirstName().isEmpty()
				|| billingAddress.getBillingAddressLastName().isEmpty()
				|| billingAddress.getBillingAddressZipcode().isEmpty()
				|| billingAddress.getBillingAddressPhone().isEmpty()) {
			//return "redirect:/checkout?id=" + shoppingCart.getId() + "&missingRequiredField=true";
			model.addAttribute("missingRequiredField", true);
			return ResponseEntity.ok(model);
		}*/
			
		
		User user = userService.findUserByEmail(checkoutDto.getUsername());
		
		Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, checkoutDto.getShippingMethod(), user);
		
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		
		if (checkoutDto.getShippingMethod().equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		} else {
			estimatedDeliveryDate = today.plusDays(3);
		}
		
		model.put("estimatedDeliveryDate", estimatedDeliveryDate);
		
		//return "orderSubmittedPage";
		return ResponseEntity.ok(model);
	}

	/*@RequestMapping("/setShippingAddress")
	public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId, Principal principal,
			Model model) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (userShipping.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			shippingAddressService.setByUserShipping(userShipping, shippingAddress);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();

			model.addAttribute("userShippingList", userShippingList);
			model.addAttribute("userPaymentList", userPaymentList);

			model.addAttribute("shippingAddress", shippingAddress);

			model.addAttribute("classActiveShipping", true);

			if (userPaymentList.size() == 0) {
				model.addAttribute("emptyPaymentList", true);
			} else {
				model.addAttribute("emptyPaymentList", false);
			}

			model.addAttribute("emptyShippingList", false);

			return "checkout";
		}
	}

	@RequestMapping("/setPaymentMethod")
	public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal,
			Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(userPaymentId);
		UserBilling userBilling = userPayment.getUserBilling();

		if (userPayment.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			paymentService.setByUserPayment(userPayment, payment);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			billingAddressService.setByUserBilling(userBilling, billingAddress);

			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();

			model.addAttribute("userShippingList", userShippingList);
			model.addAttribute("userPaymentList", userPaymentList);

			model.addAttribute("shippingAddress", shippingAddress);

			model.addAttribute("classActivePayment", true);

			model.addAttribute("emptyPaymentList", false);

			if (userShippingList.size() == 0) {
				model.addAttribute("emptyShippingList", true);
			} else {
				model.addAttribute("emptyShippingList", false);
			}

			return "checkout";
		}
	}*/

}
