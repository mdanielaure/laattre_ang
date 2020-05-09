package com.laattre.backen.service;

import java.util.Optional;

import com.laattre.backen.persistence.model.UserPayment;

public interface UserPaymentService {
	Optional<UserPayment> findById(Long id);
	
	void removeById(Long id);
}
