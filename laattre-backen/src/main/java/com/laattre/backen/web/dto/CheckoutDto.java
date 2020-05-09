package com.laattre.backen.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.laattre.backen.persistence.model.BillingAddress;
import com.laattre.backen.persistence.model.Payment;
import com.laattre.backen.persistence.model.ShippingAddress;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public class CheckoutDto {
	private ShippingAddress shippingAddress;
	private BillingAddress billingAddress;
	private Payment payment;
	private String billingSameAsShipping;
	private String shippingMethod;
	private String username;
	
	@JsonProperty
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	@JsonProperty
	public BillingAddress getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	@JsonProperty
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	@JsonProperty
	public String getBillingSameAsShipping() {
		return billingSameAsShipping;
	}
	public void setBillingSameAsShipping(String billingSameAsShipping) {
		this.billingSameAsShipping = billingSameAsShipping;
	}
	
	@JsonProperty
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
	@JsonProperty
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	 @Override
	    public String toString() {
	        final StringBuilder builder = new StringBuilder();
	        builder.append("CheckoutDto [shippingAddress=").append(shippingAddress).append(", billingAddress=").append(billingAddress).append(", payment=").append(payment).append(", shippingMethod=").append(shippingMethod).append(", username=").append(username).append(", billingSameAsShipping=")
	                .append(billingSameAsShipping).append("]");
	        return builder.toString();
	    }


	
	
}
