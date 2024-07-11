package com.microservice.entidadms.utils.validator;

import com.microservice.entidadms.utils.annotations.Cedula;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CedulaValidation implements ConstraintValidator<Cedula, String> {

    private static final String CEDULA_PATTERN = "^[0-9]{10}$"; // Expresión regular para cédula ecuatoriana

    @Override
    public void initialize(Cedula constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cedula, ConstraintValidatorContext context) {
        if (cedula == null) {
            return false;
        }
        return cedula.matches(CEDULA_PATTERN) && validarChecksum(cedula);
    }

    // Método para validar el dígito verificador
    private boolean validarChecksum(String cedula) {
        int suma = 0;
        int longitud = cedula.length();
        for (int i = 0; i < longitud - 1; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
        }
        int ultimoDigito = Character.getNumericValue(cedula.charAt(longitud - 1));
        int checksum = 10 - (suma % 10);
        if (checksum == 10) {
            checksum = 0;
        }
        return checksum == ultimoDigito;
    }

}