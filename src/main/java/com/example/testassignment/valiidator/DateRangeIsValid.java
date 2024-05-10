package com.example.testassignment.valiidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeIsValid {
    String message() default "'From` must be before `To`.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
