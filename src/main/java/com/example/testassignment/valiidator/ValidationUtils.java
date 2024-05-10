package com.example.testassignment.valiidator;

import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtils {

    public static void addConstraintViolation(String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
