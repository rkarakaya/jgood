package com.akarge.jgood.rest.controller.user.response;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ramazan Karakaya
 * 
 */
public class UserProfileResponse {
	private Set<String> roles = new HashSet<String>();

	public void addRole(String role) {
		roles.add(role);
	}

	public Set<String> getRoles() {
		return roles;
	}

	
}
