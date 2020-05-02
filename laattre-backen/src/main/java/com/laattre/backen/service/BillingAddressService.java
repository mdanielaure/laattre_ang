package com.laattre.backen.service;

import com.laattre.backen.persistence.model.BillingAddress;
import com.laattre.backen.persistence.model.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
