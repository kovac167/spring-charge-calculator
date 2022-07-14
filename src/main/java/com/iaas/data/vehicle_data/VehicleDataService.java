package com.iaas.data.vehicle_data;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Log
@Service
public class VehicleDataService {

	private final VehicleDataRepository vehicleDataRepository;

	public List<VehicleData> findAll() {
		return vehicleDataRepository.findAll();
	}

	public VehicleData findById(String id) {
		Optional<VehicleData> optional = vehicleDataRepository.findById(id);
		if (optional.isPresent()) return optional.get();
		return null;
	}

	public void saveAndFlush(VehicleData vehicleData) {
		if (vehicleData == null) {
			log.warning("entity to save is null!");
			return;
		}
		vehicleDataRepository.saveAndFlush(vehicleData);
	}
}
