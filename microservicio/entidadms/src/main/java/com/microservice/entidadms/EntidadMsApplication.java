package com.microservice.entidadms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.microservice.entidadms",
        "com.microservice.entidadms.configurations"})
public class EntidadMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntidadMsApplication.class, args);
	}

}
