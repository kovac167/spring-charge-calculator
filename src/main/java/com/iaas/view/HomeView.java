package com.iaas.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeView {
	
	@GetMapping("/")
	public String redirect() {
		return "redirect:/view/home";
	}

	@GetMapping("/view/home")
	public String view() {
		return "pages/home";
	}
}
