package com.example.testassignment.valiidator;

import com.example.testassignment.service.UserService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UniqueEmailValidatorTest {
    private UserService userService;
    private UniqueEmailValidator validator;
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        validator = new UniqueEmailValidator(userService);
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
    }

    @Test
    void isValidUniqueEmail() {
        when(userService.isEmailNotUnique(anyString())).thenReturn(false);

        assertTrue(validator.isValid("test@email.com", context));
        verifyNoInteractions(builder);
    }

    @Test
    void isValidNotUniqueEmail() {
        when(userService.isEmailNotUnique(anyString())).thenReturn(true);

        assertFalse(validator.isValid("test@email.com", context));
        verify(builder).addConstraintViolation();
        verifyNoMoreInteractions(builder);
    }
}