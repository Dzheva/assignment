package com.example.testassignment.valiidator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@PropertySource("classpath:application.properties")
class UserAgeValidatorTest {
    @Value("${minimum.age}")
    private int minimumAge;
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;
    private UserAgeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserAgeValidator();
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
    }

    @Test
    void isValidWithValidBirthDate() {
        LocalDate validBirthDate = LocalDate.now().minusYears(minimumAge + 1);

        assertTrue(validator.isValid(validBirthDate, context));
        verifyNoInteractions(builder);
    }

    @Test
    void isValidWithInvalidBirthDate() {
        LocalDate invalidBirthDate = LocalDate.now().minusYears(minimumAge - 1);

        assertFalse(validator.isValid(invalidBirthDate, context));
        verify(builder).addConstraintViolation();
        verifyNoMoreInteractions(builder);
    }
}