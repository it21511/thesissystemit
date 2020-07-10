package com.thesis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String redirectToLogin() {
		
		return "redirectToIndex";
		
	}
	
	@GetMapping("/index")
	public String showMyLoginPage() {
		
		return "index";
		
	}
	
}