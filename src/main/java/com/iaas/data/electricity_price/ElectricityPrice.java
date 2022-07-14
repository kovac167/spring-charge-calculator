package com.iaas.data.electricity_price;

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
@Table(name = "electricity_price")
public class ElectricityPrice {

	@Id
	private String state;

	private Float price;
}
