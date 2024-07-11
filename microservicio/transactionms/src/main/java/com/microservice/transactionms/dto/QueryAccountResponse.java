package com.microservice.transactionms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryAccountResponse  {
	@JsonProperty(value = "accountCode")
	private Integer accountCode;
	@JsonProperty(value = "accountNumber")
	private String accountNumber;
	@JsonProperty(value = "accountType")
	private String accountType;
	@JsonProperty(value = "availableBalance")
	private Float availableBalance;
	@JsonProperty(value = "status")
	private String status;
}
