package com.laattre.backen.service;

import com.laattre.backen.persistence.model.ShippingAddress;
import com.laattre.backen.persistence.model.UserShipping;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
