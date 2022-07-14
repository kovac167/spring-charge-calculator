package com.iaas.data.calculate_request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CalculateRequestDto {

	@NotNull
	private String vehicle;

	@NotNull
	private Float percent;

	@NotNull
	private String state;
}
