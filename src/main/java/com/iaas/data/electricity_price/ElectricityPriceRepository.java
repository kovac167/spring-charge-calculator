package com.iaas.data.electricity_price;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricityPriceRepository extends JpaRepository<ElectricityPrice, String> {

}
