package com.iaas.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iaas.data.vehicle_data.VehicleDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VehicleDataView {

	private final VehicleDataService vehicleDataService;

	@GetMapping("/view/vehicle_data")
	public String view(Model model) {
		model.addAttribute("vehicleDataList", vehicleDataService.findAll());

		return "pages/vehicle-data";
	}
}
