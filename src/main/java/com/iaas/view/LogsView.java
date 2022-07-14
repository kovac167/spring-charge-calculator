package com.iaas.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iaas.data.calculate_request.CalculateRequestService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogsView {
	
	private final CalculateRequestService calculateRequestService;
	
	@GetMapping("/view/logs")
	public String view(Model model) {
		model.addAttribute("calculateRequestList", calculateRequestService.findAll());

		return "pages/logs";
	}
}
