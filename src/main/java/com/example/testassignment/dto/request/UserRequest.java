package com.example.testassignment.dto.request;

import com.example.testassignment.valiidator.UniqueEmail;
import com.example.testassignment.valiidator.UserAgeIsValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserRequest {
    @NotBlank(message = "The email name cannot be blank.")
    @Email(message = "Invalid email format.")
    @UniqueEmail
    private String email;
    @NotBlank(message = "The first name cannot be blank.")
    private String firstName;
    @NotBlank(message = "The last name cannot be blank.")
    private String lastName;
    @NotNull(message = "The birth date cannot be blank.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "The birth date must be earlier than the current date.")
    @UserAgeIsValid
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
