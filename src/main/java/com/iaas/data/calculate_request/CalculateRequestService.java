package com.iaas.data.calculate_request;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Log
@Service
public class CalculateRequestService {
	
	private final CalculateRequestRepository calculateRequestRepository;
	
	public List<CalculateRequest> findAll() {
		return calculateRequestRepository.findAll();
	}
	
	public void saveAndFlush(CalculateRequest calculateRequest) {
		if (calculateRequest == null) {
			log.warning("entity to save is null!");
			return;
		}
		calculateRequestRepository.saveAndFlush(calculateRequest);
	}
}
