package com.example.testassignment.dto.request;

import com.example.testassignment.valiidator.DateRangeIsValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@DateRangeIsValid
public record BirthDateRangeRequestParameters(
        @NotNull(message = "The 'From' date cannot be blank.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @PastOrPresent(message = "The 'From' date cannot be later than the current date.")
        LocalDate from,
        @NotNull(message = "The 'To' date cannot be blank.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @PastOrPresent(message = "The 'To' date cannot be later than the current date.")
        LocalDate to) {
}
