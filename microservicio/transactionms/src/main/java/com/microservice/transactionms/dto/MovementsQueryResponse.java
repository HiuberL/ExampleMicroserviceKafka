package com.microservice.transactionms.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MovementsQueryResponse  {
	@JsonProperty("sequence")
	private Long sequence;
	@JsonProperty("date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date date;
	@JsonProperty("type")
	private String type;
	@JsonProperty("value")
	private Float value;
	@JsonProperty("availableBalance")
	private Float availableBalance;
}
