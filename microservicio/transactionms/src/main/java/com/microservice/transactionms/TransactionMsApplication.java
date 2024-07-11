package com.microservice.transactionms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.microservice.transactionms",
        "com.microservice.transactionms.configurations"})
public class TransactionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMsApplication.class, args);
	}

}
