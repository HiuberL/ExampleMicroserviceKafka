package com.microservice.entidadms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePersonRequest {
	private String name;
	private String lastName;
	private String sLastName;
	private String gender;
	private Integer age;
	private String address;
	private String phone;   
	private String password;
	private String state;
}
