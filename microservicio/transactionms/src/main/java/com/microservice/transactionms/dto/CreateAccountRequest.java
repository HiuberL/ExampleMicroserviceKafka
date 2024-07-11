package com.microservice.transactionms.dto;


import com.microservice.transactionms.utils.annotations.Account;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAccountRequest {
	@Account(message = "Tipo de cuenta solicitada inválida")
	private String type;
}
