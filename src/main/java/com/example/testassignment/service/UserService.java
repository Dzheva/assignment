package com.example.testassignment.service;

import com.example.testassignment.exception.ResourceNotFoundException;
import com.example.testassignment.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Map<Long, User> userMap = new HashMap<>();
    private long nextId = 1;

    public User createUser(User user) {
        user.setId(nextId++);
        userMap.putIfAbsent(user.getId(), user);
        return userMap.get(user.getId());
    }

    public User getUserById(Long id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new ResourceNotFoundException("User with id = " + id + " not found");
        }

        return user;
    }

    public boolean deleteUserById(Long id) {
        if (userMap.containsKey(id)) {
            userMap.remove(id);
            return true;
        }

        return false;
    }

    public User patchUserById(Long id, User user) {
        User patchedUser = getUserById(id);

        if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().equals(patchedUser.getEmail())) {
            patchedUser.setEmail(user.getEmail());
        }

        if (user.getFirstName() != null && !user.getFirstName().isEmpty() && !user.getFirstName().equals(patchedUser.getFirstName())) {
            patchedUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null && !user.getLastName().isEmpty() && !user.getLastName().equals(patchedUser.getLastName())) {
            patchedUser.setLastName(user.getLastName());
        }

        if (user.getBirthDate() != null && !user.getBirthDate().equals(patchedUser.getBirthDate())) {
            patchedUser.setBirthDate(user.getBirthDate());
        }

        if (user.getAddress() != null && !user.getAddress().isEmpty() && !user.getAddress().equals(patchedUser.getAddress())) {
            patchedUser.setAddress(user.getAddress());
        }

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty() && !user.getPhoneNumber().equals(patchedUser.getPhoneNumber())) {
            patchedUser.setPhoneNumber(user.getPhoneNumber());
        }

        return patchedUser;
    }

    public User updateUserById(Long id, User user) {
        User updatedUser = getUserById(id);
        BeanUtils.copyProperties(updatedUser, user, "id");
        return updatedUser;
    }

    public List<User> getUsersByBirthDateRange(LocalDate from, LocalDate to) {
        return userMap.values().stream()
                .filter(user -> user.getBirthDate().compareTo(from) >= 0 && user.getBirthDate().compareTo(to) <= 0)
                .collect(Collectors.toList());
    }

    public boolean isEmailNotUnique(String email) {
        return userMap.values().stream()
                .anyMatch(existingUser -> existingUser.getEmail().equals(email));
    }

}
