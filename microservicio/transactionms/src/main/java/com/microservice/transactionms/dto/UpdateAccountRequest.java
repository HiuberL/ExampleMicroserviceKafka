package com.microservice.transactionms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateAccountRequest {
	private Float availableBalance;
	private String status;

}
