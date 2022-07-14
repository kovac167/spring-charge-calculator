package com.iaas.data.vehicle_data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_data")
public class VehicleData {

	@Id
	private String model;

	private Float kwh;
}
