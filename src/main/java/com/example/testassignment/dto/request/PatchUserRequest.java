package com.example.testassignment.dto.request;

import com.example.testassignment.valiidator.UniqueEmail;
import com.example.testassignment.valiidator.UserAgeIsValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PatchUserRequest {
    @Email(message = "Invalid email format.")
    @UniqueEmail
    private String email;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "The birth date must be earlier than the current date.")
    @UserAgeIsValid
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
