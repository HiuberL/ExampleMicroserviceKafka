package com.microservice.transactionms.utils.validator;

import com.microservice.transactionms.utils.annotations.Account;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountValidation implements ConstraintValidator<Account, String> {


    @Override
    public void initialize(Account constraintAnnotation) {
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext context) {
        if (type == null) {
            return false;
        }
        return validAccount(type);
    }

    // Método para validar el dígito verificador
    private boolean validAccount(String type) {
        if (type.equals("AHO") || type.equals("CTE"))
            return true;        
        return false;
    }

}