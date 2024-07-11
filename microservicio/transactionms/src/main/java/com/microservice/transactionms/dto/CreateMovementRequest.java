package com.microservice.transactionms.dto;





import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CreateMovementRequest {
	@NotNull(message = "El codigo de cliente es obligatorio")
	private Integer codeClient;
	@Pattern(regexp="^[0-9]*$",message = "La cuenta debe ser numérica")
	private String account;
	@NotNull(message = "El valor no puede ser nulo")
	private Float value;
	private String type;
}
