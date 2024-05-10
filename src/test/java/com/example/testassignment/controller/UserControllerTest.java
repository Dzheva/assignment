package com.example.testassignment.controller;

import com.example.testassignment.dto.UserMapper;
import com.example.testassignment.dto.request.BirthDateRangeRequestParameters;
import com.example.testassignment.dto.request.PatchUserRequest;
import com.example.testassignment.dto.request.UserRequest;
import com.example.testassignment.dto.response.UserResponse;
import com.example.testassignment.model.User;
import com.example.testassignment.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        Long userId = 1L;
        UserRequest request = new UserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setBirthDate(LocalDate.of(1990, 5, 20));

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 20));

        User createdUser = new User();
        createdUser.setId(userId);
        createdUser.setFirstName("John");
        createdUser.setLastName("Doe");
        createdUser.setEmail("john.doe@example.com");
        createdUser.setBirthDate(LocalDate.of(1990, 5, 20));

        BindingResult bindingResult = new DirectFieldBindingResult(request, "userRequest");

        when(userMapper.userRequestToUser(request)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(createdUser);

        ResponseEntity response = userController.createUser(request, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(response.getHeaders().getLocation().toString(), "/users/1");
    }

    @Test
    void getUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 20));

        UserResponse expectedUserResponse = new UserResponse();
        expectedUserResponse.setId(userId);
        expectedUserResponse.setFirstName("John");
        expectedUserResponse.setLastName("Doe");
        expectedUserResponse.setEmail("john.doe@example.com");
        expectedUserResponse.setBirthDate(LocalDate.of(1990, 5, 20));

        when(userService.getUserById(userId)).thenReturn(user);
        when(userMapper.userToUserResponse(user)).thenReturn(expectedUserResponse);

        ResponseEntity<UserResponse> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedUserResponse, response.getBody());
    }

    @Test
    void deleteUser() {
        when(userService.deleteUserById(1L)).thenReturn(true);
        when(userService.deleteUserById(5L)).thenReturn(false);

        ResponseEntity response = userController.deleteUser(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity deleteNonExistingUserResponse = userController.deleteUser(5L);
        assertEquals(HttpStatus.NOT_FOUND, deleteNonExistingUserResponse.getStatusCode());
    }

    @Test
    void patchUser() {
        Long userId = 1L;
        PatchUserRequest request = new PatchUserRequest();
        request.setFirstName("Bob");
        request.setEmail("");

        User user = new User();
        user.setFirstName("Bob");
        user.setEmail("");

        User patchedUser = new User();
        patchedUser.setId(userId);
        patchedUser.setFirstName("Bob");
        patchedUser.setLastName("Doe");
        patchedUser.setEmail("john.doe@example.com");
        patchedUser.setBirthDate(LocalDate.of(1990, 5, 20));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName("Bob");
        userResponse.setLastName("Doe");
        userResponse.setEmail("john.doe@example.com");
        userResponse.setBirthDate(LocalDate.of(1990, 5, 20));

        BindingResult bindingResult = new DirectFieldBindingResult(request, "patchUserRequest");

        when(userMapper.patchUserRequestToUser(request)).thenReturn(user);
        when(userService.patchUserById(userId, user)).thenReturn(patchedUser);
        when(userMapper.userToUserResponse(patchedUser)).thenReturn(userResponse);

        ResponseEntity response = userController.patchUser(userId, request, bindingResult);
        UserResponse responseBody = (UserResponse) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getFirstName(), responseBody.getFirstName());
        assertNotEquals(request.getEmail(), responseBody.getEmail());
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Jane");
        userRequest.setEmail("jane.doe@example.com");

        User user = new User();
        user.setFirstName("Jane");
        user.setEmail("jane.doe@example.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setBirthDate(LocalDate.of(1990, 5, 20));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName("Jane");
        userResponse.setLastName("Doe");
        userResponse.setEmail("jane.doe@example.com");
        userResponse.setBirthDate(LocalDate.of(1990, 5, 20));

        BindingResult bindingResult = new DirectFieldBindingResult(userRequest, "userRequest");

        when(userMapper.userRequestToUser(userRequest)).thenReturn(user);
        when(userService.updateUserById(userId, user)).thenReturn(updatedUser);
        when(userMapper.userToUserResponse(updatedUser)).thenReturn(userResponse);

        ResponseEntity response = userController.updateUser(userId, userRequest, bindingResult);
        UserResponse responseBody = (UserResponse) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, responseBody.getId());
        assertEquals("Jane", responseBody.getFirstName());
        assertEquals("jane.doe@example.com", responseBody.getEmail());

    }

    @Test
    void searchUsersByBirthDateRange() {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);
        BirthDateRangeRequestParameters dateRange = new BirthDateRangeRequestParameters(from, to);
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setBirthDate(LocalDate.of(1990, 5, 20));
        users.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setBirthDate(LocalDate.of(1990, 8, 15));
        users.add(user2);

        when(userService.getUsersByBirthDateRange(from, to)).thenReturn(users);
        when(userMapper.userToUserResponse(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setEmail(user.getEmail());
            userResponse.setBirthDate(user.getBirthDate());
            return userResponse;
        });

        BindingResult bindingResult = new DirectFieldBindingResult(dateRange, "BirthDateRangeRequestParameters");

        ResponseEntity response = userController.searchUsersByBirthDateRange(dateRange, bindingResult);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<UserResponse> responseBody = (List<UserResponse>) response.getBody();
        assertEquals(2, responseBody.size());

        UserResponse userResponse1 = responseBody.get(0);
        assertEquals(Long.valueOf(1), userResponse1.getId());
        assertEquals("John", userResponse1.getFirstName());

        UserResponse userResponse2 = responseBody.get(1);
        assertEquals(Long.valueOf(2), userResponse2.getId());
        assertEquals("Jane", userResponse2.getFirstName());

    }

    @Test
    void handleValidationErrors() {
        UserRequest userRequest = new UserRequest();
        BindingResult bindingResult = new DirectFieldBindingResult(userRequest, "userRequest");
        bindingResult.rejectValue("firstName", "NotNull", "First name cannot be null");

        ResponseEntity responseEntity = userController.handleValidationErrors(bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        List<String> errors = (List<String>) responseEntity.getBody();
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("First name cannot be null", errors.get(0));
    }

}