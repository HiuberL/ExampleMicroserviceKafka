package com.microservice.entidadms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryPersonResponse  {
	@JsonProperty(value = "personCode")
	private String personCode;
	@JsonProperty(value = "dni")
	private String dni;
	@JsonProperty(value = "name")
	private String name;
	@JsonProperty(value = "lastName")
	private String lastName;
	@JsonProperty(value = "sLastName")
	private String sLastName;
	@JsonProperty(value = "gender")
	private String gender;
	@JsonProperty(value = "age")
	private Integer age;
	@JsonProperty(value = "address")
	private String address;
	@JsonProperty(value = "phone")
	private String phone;   
	@JsonProperty(value = "dateModify")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
	private Date dateModify;
	@JsonProperty(value = "dateRegister")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
	private Date dateRegister;
	@JsonProperty(value = "state")
	private String state;
}
