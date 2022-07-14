package com.iaas.data.calculate_request;

public final class CalculateRequestMapper {

	private CalculateRequestMapper() {
	}

	public static CalculateRequest dtoToEntity(CalculateRequestDto dto) {
		CalculateRequest enitity = new CalculateRequest();
		enitity.setVehicle(dto.getVehicle());
		enitity.setPercent(dto.getPercent());
		enitity.setState(dto.getState());
		return enitity;
	}

	public static CalculateRequestDto entityToDto(CalculateRequest entity) {
		CalculateRequestDto dto = new CalculateRequestDto();
		dto.setVehicle(entity.getVehicle());
		dto.setPercent(entity.getPercent());
		dto.setState(entity.getState());
		return dto;
	}
}
