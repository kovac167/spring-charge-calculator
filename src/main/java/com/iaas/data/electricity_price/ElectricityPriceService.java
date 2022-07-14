package com.iaas.data.electricity_price;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Log
@Service
public class ElectricityPriceService {

	private final ElectricityPriceRepository electricityPriceRepository;

	public List<ElectricityPrice> findAll() {
		return electricityPriceRepository.findAll(Sort.by(Sort.Direction.ASC, "state"));
	}

	public ElectricityPrice findById(String id) {
		Optional<ElectricityPrice> optional = electricityPriceRepository.findById(id);
		if (optional.isPresent()) return optional.get();
		return null;
	}

	public void saveAndFlush(ElectricityPrice electricityPrice) {
		if (electricityPrice == null) {
			log.warning("entity to save is null!");
			return;
		}
		electricityPriceRepository.saveAndFlush(electricityPrice);
	}

	public void loadStats(JSONArray stats) {
		for (int i = 0; i < stats.length(); i++) {
			JSONObject stat = stats.getJSONObject(i);
			if (!stat.getString("sectorid").contains("RES")) continue;

			ElectricityPrice electricityCost = new ElectricityPrice();
			electricityCost.setState(stat.getString("stateid"));
			electricityCost.setPrice(stat.getFloat("price"));

			saveAndFlush(electricityCost);
		}
	}

	public List<String> findListOfStates() {
		List<String> stateList = new ArrayList<>();
		List<ElectricityPrice> electricityPriceList = findAll();

		for (ElectricityPrice electricityPrice : electricityPriceList) {
			String state = electricityPrice.getState();
			stateList.add(state);
		}
		return stateList;
	}
}
