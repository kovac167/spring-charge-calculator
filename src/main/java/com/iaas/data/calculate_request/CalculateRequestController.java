package com.iaas.data.calculate_request;

import java.text.DecimalFormat;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iaas.data.electricity_price.ElectricityPrice;
import com.iaas.data.electricity_price.ElectricityPriceService;
import com.iaas.data.vehicle_data.VehicleData;
import com.iaas.data.vehicle_data.VehicleDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@RequestMapping("api")
@RequiredArgsConstructor
@Log
public class CalculateRequestController {

	private final ElectricityPriceService electricityCostService;

	private final VehicleDataService vehicleDataService;
	
	private final CalculateRequestService calculateRequestService;

	@PostMapping(path = "/calculate_request", produces = "application/json")
	public @ResponseBody ResponseEntity<CalculateRequest> post(
			@Valid @RequestBody CalculateRequestDto body) {

		log.info("/calculate_request, post");
		log.info("input: " + body);

		CalculateRequest calculateRequest = CalculateRequestMapper.dtoToEntity(body);

		ElectricityPrice electricityCost = electricityCostService.findById(calculateRequest.getState());
		VehicleData vehicleData = vehicleDataService.findById(calculateRequest.getVehicle());
		
		Float percent = calculateRequest.getPercent();
		float percentDecimal = (float) (percent * .01);

		Float capacity = vehicleData.getKwh();
		calculateRequest.setCapacityKwh(capacity);

		Float price = electricityCost.getPrice();
		float costPerKwh = (float) (price * .01);
		calculateRequest.setCostPerKwh(costPerKwh);

		Float result = capacity * costPerKwh * percentDecimal;
		DecimalFormat df = new DecimalFormat("0.00");
		calculateRequest.setResult(df.format(result));
		
		calculateRequestService.saveAndFlush(calculateRequest);

		log.info("response: " + calculateRequest);
		return ResponseEntity.ok().body(calculateRequest);
	}
}
