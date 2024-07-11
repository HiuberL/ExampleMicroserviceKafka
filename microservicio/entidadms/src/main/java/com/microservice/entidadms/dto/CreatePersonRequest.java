package com.microservice.entidadms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.microservice.entidadms.utils.annotations.Cedula;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePersonRequest {
	@Cedula(message = "Cédula es inválida")
	private String dni;
	@NotBlank(message = "Nombre de persona es requerida")
	private String name;
	@NotBlank(message = "Apellido Paterno de persona es requerida")
	private String lastName;
	private String sLastName;
	@NotBlank(message = "Genero de persona es requerida")
	private String gender;
	@NotNull(message = "Edad de persona es requerida")
	private Integer age;
	private String address;
	@Pattern(regexp = "^[0-9]{10}$",message = "Telefono debe ser númerico de 10 dígitos")
	private String phone;   
	@NotNull(message = "Contrasenia es requerida")
	private String password;
}
