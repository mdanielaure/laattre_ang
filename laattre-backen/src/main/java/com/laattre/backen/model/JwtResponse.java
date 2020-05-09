package com.laattre.backen.model;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

import com.laattre.backen.persistence.model.User;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private UserDetails userDetails;
	private User user;

	public JwtResponse(String jwttoken, UserDetails userDetails, User user) {
		this.jwttoken = jwttoken;
		this.userDetails = userDetails;
		this.user = user;
	}

	public String getToken() {
		return this.jwttoken;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}

	public User getUser() {
		return user;
	}
}