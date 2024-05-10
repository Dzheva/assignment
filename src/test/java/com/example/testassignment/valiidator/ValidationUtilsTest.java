package com.example.testassignment.valiidator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidationUtilsTest {

    @Test
    void addConstraintViolation() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);

        ValidationUtils.addConstraintViolation("Test message", context);

        verify(context).disableDefaultConstraintViolation();
        verify(builder).addConstraintViolation();
    }
}