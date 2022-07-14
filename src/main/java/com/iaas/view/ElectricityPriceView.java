package com.iaas.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iaas.data.electricity_price.ElectricityPriceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ElectricityPriceView {

	private final ElectricityPriceService electricityPriceService;

	@GetMapping("/view/electricity_price")
	public String view(Model model) {
		model.addAttribute("electricityPriceList", electricityPriceService.findAll());

		return "pages/electricity-price";
	}
}
