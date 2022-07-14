package com.iaas.view;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iaas.data.calculate_request.CalculateRequest;
import com.iaas.data.calculate_request.CalculateRequestController;
import com.iaas.data.calculate_request.CalculateRequestDto;
import com.iaas.data.calculate_request.CalculateRequestMapper;
import com.iaas.data.electricity_price.ElectricityPriceService;
import com.iaas.data.vehicle_data.VehicleDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CalculateView {

	private final CalculateRequestController calculateRequestController;

	private final ElectricityPriceService electricityPriceService;

	private final VehicleDataService vehicleDataService;

	@GetMapping("/view/calculate")
	public String view(Model model) {
		CalculateRequest calculateRequest = new CalculateRequest();

		model.addAttribute("calculateRequest", calculateRequest);
		model.addAttribute("stateList", electricityPriceService.findListOfStates());
		model.addAttribute("vehicleData", vehicleDataService.findAll());
		
		return "pages/calculate";
	}

	@PostMapping("/view/calculate")
	public String submitForm(@ModelAttribute("calculateRequest") CalculateRequest calculateRequest) {
		CalculateRequestDto calculateRequestDto = CalculateRequestMapper.entityToDto(calculateRequest);

		ResponseEntity<CalculateRequest> response = calculateRequestController.post(calculateRequestDto);
		
		calculateRequest.setCapacityKwh(response.getBody().getCapacityKwh());
		calculateRequest.setCostPerKwh(response.getBody().getCostPerKwh());
		calculateRequest.setResult(response.getBody().getResult());

		return "pages/calculate-result";
	}
}
