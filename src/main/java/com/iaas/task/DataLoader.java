package com.iaas.task;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.iaas.data.electricity_price.ElectricityPriceService;
import com.iaas.data.vehicle_data.VehicleData;
import com.iaas.data.vehicle_data.VehicleDataService;
import com.iaas.utility.opendata.OpendataUtility;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@ConfigurationProperties(prefix = "app")
@Data
@Log
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

	// variable mapped to app.vehicle-metadata property using @ConfigurationProperties & @Data
	private Map<String, Integer> vehicleMetadata;

	private final OpendataUtility opendataUtility;

	private final VehicleDataService vehicleDataService;

	private final ElectricityPriceService electricityPriceService;

	public void run(ApplicationArguments args) throws IOException, ProcessingException {
		loadVehicleData();
		loadElectricityPrice();
		log.info("data load complete.");
	}

	private void loadVehicleData() {
		log.info("loading data to table: vehicle_data...");

		vehicleMetadata.forEach((vehicle, kwh) -> {
			vehicleDataService.saveAndFlush(new VehicleData(vehicle, (float) kwh));
		});

		int vehicleDataCount = vehicleDataService.findAll().size();
		log.info("elements loaded: " + vehicleDataCount);
	}

	private void loadElectricityPrice() throws IOException, ProcessingException {
		log.info("loading data to table: electricity_price...");

		JSONArray stats = opendataUtility.buildStats();
		electricityPriceService.loadStats(stats);

		int electricityPriceCount = electricityPriceService.findAll().size();
		log.info("elements loaded: " + electricityPriceCount);
	}
}
