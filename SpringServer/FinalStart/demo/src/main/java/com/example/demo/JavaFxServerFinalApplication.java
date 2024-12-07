package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@AutoConfigurationPackage(basePackages = {"com.example.demo", "com.example.demo.Utils"})
public class JavaFxServerFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaFxServerFinalApplication.class, args);
	}

}
