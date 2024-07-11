package com.microservice.entidadms.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.microservice.entidadms.utils.validator.CedulaValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CedulaValidation.class)
public @interface Cedula {

    String message() default "Número de cédula inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}