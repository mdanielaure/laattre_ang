package com.laattre.backen.service;

import java.util.Optional;

import com.laattre.backen.persistence.model.UserShipping;

public interface UserShippingService {
	Optional<UserShipping> findById(Long id);
	
	void removeById(Long id);
}
