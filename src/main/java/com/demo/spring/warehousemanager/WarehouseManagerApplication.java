package com.demo.spring.warehousemanager;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseManagerApplication {

	public static void main(String[] args) {

		org.slf4j.Logger logger = LoggerFactory.getLogger(WarehouseManagerApplication.class);

		SpringApplication.run(WarehouseManagerApplication.class, args);

	}

}
