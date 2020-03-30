package com.kaizerwebdesign.hotelapp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/web")
	public String loginForm() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (!(auth instanceof AnonymousAuthenticationToken)) {
	        	return "redirect:/web/home";
	        }else {
	        	return "login-form";
	        }
	}
	
	@GetMapping("/")
	public String loginForm1() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (!(auth instanceof AnonymousAuthenticationToken)) {
	        	return "redirect:/web/home";
	        }else {
	        	return "login-form";
	        }
	}
}
