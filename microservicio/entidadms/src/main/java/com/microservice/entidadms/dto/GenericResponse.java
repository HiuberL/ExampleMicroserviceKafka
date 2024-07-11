package com.microservice.entidadms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.entidadms.utils.ResponseDictionary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

    @Schema(description = "Código de estado de la respuesta")
    @JsonProperty("code")
    private int code;
    @Schema(description = "Mensaje de la respuesta")
    @JsonProperty("message")
    private String message;
    @Schema(description = "Datos de la respuesta")
    @JsonProperty("data")
    private T data;
    @JsonIgnore
    private int responseCode;
    
    public GenericResponse(ResponseDictionary dictionary){
        this.code = dictionary.getCodeInter();
        this.message = dictionary.getMessage();
        this.responseCode = dictionary.getCodeHttp();
    }

}
