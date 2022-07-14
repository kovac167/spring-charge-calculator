package com.iaas.task;

import org.json.JSONArray;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iaas.data.electricity_price.ElectricityPriceService;
import com.iaas.utility.opendata.OpendataUtility;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@Data
@EnableScheduling
@Log
@RequiredArgsConstructor
public class JobScheduler {

	private final OpendataUtility opendataUtility;

	private final ElectricityPriceService electricityPriceService;

	@Scheduled(cron = "0 0 7 * * 1")
	protected void runWeekly() {
		log.info("running weekly jobs...");

		log.info("updating data in table: electricity_price");
		try {
			JSONArray stats = opendataUtility.buildStats();
			electricityPriceService.loadStats(stats);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("weekly jobs complete.");
	}
}
