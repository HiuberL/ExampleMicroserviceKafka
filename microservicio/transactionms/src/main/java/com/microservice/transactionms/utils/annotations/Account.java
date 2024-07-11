package com.microservice.transactionms.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.microservice.transactionms.utils.validator.AccountValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountValidation.class)
public @interface Account {

    String message() default "Tipo de cuenta Inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}