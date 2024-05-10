package com.example.testassignment.controller;

import com.example.testassignment.dto.UserMapper;
import com.example.testassignment.dto.request.BirthDateRangeRequestParameters;
import com.example.testassignment.dto.request.PatchUserRequest;
import com.example.testassignment.dto.request.UserRequest;
import com.example.testassignment.dto.response.UserResponse;
import com.example.testassignment.model.User;
import com.example.testassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity createUser(@Validated @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (handleValidationErrors(bindingResult) != null) {
            return handleValidationErrors(bindingResult);
        } else {
            User user = userMapper.userRequestToUser(userRequest);
            User createdUser = userService.createUser(user);

            URI location = UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(createdUser.getId()).toUri();

            return ResponseEntity.created(location).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = userMapper.userToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity patchUser(@PathVariable("id") Long id, @Validated @RequestBody PatchUserRequest patchUserRequest
            , BindingResult bindingResult) {
        if (handleValidationErrors(bindingResult) != null) {
            return handleValidationErrors(bindingResult);
        } else {
            User user = userMapper.patchUserRequestToUser(patchUserRequest);
            User patchedUser = userService.patchUserById(id, user);
            UserResponse userResponse = userMapper.userToUserResponse(patchedUser);
            return ResponseEntity.ok(userResponse);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @Validated @RequestBody UserRequest userRequest,
                                     BindingResult bindingResult) {
        if (handleValidationErrors(bindingResult) != null) {
            return handleValidationErrors(bindingResult);
        } else {
            User user = userMapper.userRequestToUser(userRequest);
            User updatedUser = userService.updateUserById(id, user);
            UserResponse userResponse = userMapper.userToUserResponse(updatedUser);
            return ResponseEntity.ok(userResponse);
        }
    }

    @GetMapping
    public ResponseEntity searchUsersByBirthDateRange(@Validated BirthDateRangeRequestParameters dateRange, BindingResult bindingResult) {
        if (handleValidationErrors(bindingResult) != null) {
            return handleValidationErrors(bindingResult);
        } else {
            List<User> users = userService.getUsersByBirthDateRange(dateRange.from(), dateRange.to());
            List<UserResponse> userResponses = users.stream()
                    .map(userMapper::userToUserResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponses);
        }
    }

    public ResponseEntity handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return null;
    }
}
