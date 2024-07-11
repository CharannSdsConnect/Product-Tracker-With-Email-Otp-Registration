package com.charann.producttrackerapi.entity;

import lombok.Getter;

@Getter
public class JwtResponse {
	
	private final String jwtToken;

	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public JwtResponse() throws IllegalAccessException {
		throw new IllegalAccessException("Validate your email!");
	}


}
