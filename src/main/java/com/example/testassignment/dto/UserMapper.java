package com.example.testassignment.dto;

import com.example.testassignment.dto.request.PatchUserRequest;
import com.example.testassignment.dto.request.UserRequest;
import com.example.testassignment.dto.response.UserResponse;
import com.example.testassignment.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User userRequestToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public User patchUserRequestToUser(PatchUserRequest patchUserRequest) {
        return modelMapper.map(patchUserRequest, User.class);
    }

    public UserResponse userToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
