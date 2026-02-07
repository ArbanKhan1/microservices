package com.mother;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MotherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotherApplication.class, args);
	}

}
