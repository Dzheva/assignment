package com.example.testassignment.valiidator;

import com.example.testassignment.dto.request.BirthDateRangeRequestParameters;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class DateRangeValidatorTest {

    private BirthDateRangeRequestParameters validDate;
    private BirthDateRangeRequestParameters invalidDate;
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;
    private DateRangeValidator validator;

    @BeforeEach
    void setUp() {
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        validator = new DateRangeValidator();
    }

    @Test
    void isValidValidRange() {
        validDate = new BirthDateRangeRequestParameters(LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 12, 31));

        assertTrue(validator.isValid(validDate, context));
        verifyNoInteractions(context);
    }

    @Test
    void isValidInvalidRange() {
        invalidDate = new BirthDateRangeRequestParameters(LocalDate.of(2001, 1, 1),
                LocalDate.of(2000, 12, 31));

        assertFalse(validator.isValid(invalidDate, context));
        verify(builder).addConstraintViolation();
        verifyNoMoreInteractions(builder);
    }
}