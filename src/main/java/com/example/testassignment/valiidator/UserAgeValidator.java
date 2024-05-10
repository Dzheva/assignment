package com.example.testassignment.valiidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@PropertySource("classpath:application.properties")
public class UserAgeValidator implements ConstraintValidator<UserAgeIsValid, LocalDate> {

    @Value("${minimum.age}")
    private int minimumAge;

    @Override
    public void initialize(UserAgeIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        LocalDate minimumBirthDate = now.minusYears(minimumAge);

        if (birthDate != null && birthDate.isAfter(minimumBirthDate)) {
            String errorMessage = String.format("User age must be at least %d years old.", minimumAge);
            ValidationUtils.addConstraintViolation(errorMessage, context);
            return false;
        }

        return true;
    }

}

