package com.laattre.backen.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BillingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String BillingAddressFirstName;
	private String BillingAddressLastName;
	private String BillingAddressStreet1;
	private String BillingAddressStreet2;
	private String BillingAddressCity;
	private String BillingAddressState;
	private String BillingAddressCountry;
	private String BillingAddressZipcode;
	private String BillingAddressPhone;
	
	@OneToOne
	@JsonIgnore
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getBillingAddressStreet1() {
		return BillingAddressStreet1;
	}

	public void setBillingAddressStreet1(String billingAddressStreet1) {
		BillingAddressStreet1 = billingAddressStreet1;
	}

	public String getBillingAddressStreet2() {
		return BillingAddressStreet2;
	}

	public void setBillingAddressStreet2(String billingAddressStreet2) {
		BillingAddressStreet2 = billingAddressStreet2;
	}

	public String getBillingAddressCity() {
		return BillingAddressCity;
	}

	public void setBillingAddressCity(String billingAddressCity) {
		BillingAddressCity = billingAddressCity;
	}

	public String getBillingAddressState() {
		return BillingAddressState;
	}

	public void setBillingAddressState(String billingAddressState) {
		BillingAddressState = billingAddressState;
	}

	public String getBillingAddressCountry() {
		return BillingAddressCountry;
	}

	public void setBillingAddressCountry(String billingAddressCountry) {
		BillingAddressCountry = billingAddressCountry;
	}

	public String getBillingAddressZipcode() {
		return BillingAddressZipcode;
	}

	public void setBillingAddressZipcode(String billingAddressZipcode) {
		BillingAddressZipcode = billingAddressZipcode;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getBillingAddressPhone() {
		return BillingAddressPhone;
	}

	public void setBillingAddressPhone(String billingAddressPhone) {
		BillingAddressPhone = billingAddressPhone;
	}

	public String getBillingAddressLastName() {
		return BillingAddressLastName;
	}

	public void setBillingAddressLastName(String billingAddressLastName) {
		BillingAddressLastName = billingAddressLastName;
	}

	public String getBillingAddressFirstName() {
		return BillingAddressFirstName;
	}

	public void setBillingAddressFirstName(String billingAddressFirstName) {
		BillingAddressFirstName = billingAddressFirstName;
	}

}
