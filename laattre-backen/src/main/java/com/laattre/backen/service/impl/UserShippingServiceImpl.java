package com.laattre.backen.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laattre.backen.persistence.dao.UserShippingRepository;
import com.laattre.backen.persistence.model.UserShipping;
import com.laattre.backen.service.UserShippingService;


@Service
public class UserShippingServiceImpl implements UserShippingService{
	
	@Autowired
	private UserShippingRepository userShippingRepository;
	
	
	public Optional<UserShipping> findById(Long id) {
		return userShippingRepository.findById(id);
	}
	
	public void removeById(Long id) {
		userShippingRepository.deleteById(id);
	}

}
