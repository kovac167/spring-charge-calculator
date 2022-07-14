package com.iaas.data.calculate_request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "calculate_request")
public class CalculateRequest {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private String vehicle;

	private Float percent;

	private String state;
	
	private Float capacityKwh;
	
	private Float costPerKwh;

	private String result;
}
