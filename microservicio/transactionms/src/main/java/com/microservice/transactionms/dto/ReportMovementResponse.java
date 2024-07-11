package com.microservice.transactionms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportMovementResponse extends QueryAccountResponse  {
	@JsonProperty("movements")
	private List<MovementsQueryResponse> movements;
}
