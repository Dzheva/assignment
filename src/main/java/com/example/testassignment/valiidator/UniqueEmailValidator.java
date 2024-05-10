package com.example.testassignment.valiidator;

import com.example.testassignment.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userService.isEmailNotUnique(email)) {
            String errorMessage = String.format("Email must be unique. This email: '%s' is not unique.", email);
            ValidationUtils.addConstraintViolation(errorMessage, context);
            return false;
        }

        return true;
    }

}
