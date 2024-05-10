package com.example.testassignment.valiidator;

import com.example.testassignment.dto.request.BirthDateRangeRequestParameters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRangeIsValid, BirthDateRangeRequestParameters> {

    @Override
    public void initialize(DateRangeIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(BirthDateRangeRequestParameters date,
                           ConstraintValidatorContext context) {
        if (date.from() != null && date.to() != null && date.from().isAfter(date.to())) {
            String errorMessage = String.format("'From` must be before `To`. 'From` (%s) is after `To` (%s).", date.from(), date.to());
            ValidationUtils.addConstraintViolation(errorMessage, context);
            return false;
        }

        return true;
    }

}
