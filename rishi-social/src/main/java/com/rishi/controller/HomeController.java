package com.rishi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/home")
	public String homeControllerHandlerWithPath() {
		return "This home controller with path";
	}
	
	@GetMapping
	public String homeControllerHandler() {
		return "This home controller";
	}

}
