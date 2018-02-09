package com.akarge.jgood.rest.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akarge.jgood.rest.controller.RestControllerBase;
import com.akarge.jgood.rest.controller.user.response.UserProfileResponse;

/**
 * @author Ramazan Karakaya
 * 
 */
@Controller
public class UserController extends RestControllerBase {

	@ResponseBody
	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public UserProfileResponse getUserProfile(Authentication authentication) {
		UserProfileResponse restResponse = new UserProfileResponse();
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			restResponse.addRole(ga.getAuthority());
		}
		return restResponse;

	}
	
	@RequestMapping(value="/user/logout", method = RequestMethod.GET)
	public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	}	
}
