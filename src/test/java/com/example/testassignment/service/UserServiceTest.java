package com.example.testassignment.service;

import com.example.testassignment.exception.ResourceNotFoundException;
import com.example.testassignment.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private Map<Long, User> userMap;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setFirstName("TestName");
        testUser.setLastName("TestSurname");
        testUser.setEmail("test@gmail.com");
        testUser.setBirthDate(LocalDate.of(2000, 05, 10));
        User createdUser = userService.createUser(testUser);
    }

    @Test
    void isEmailNotUnique() {
        assertTrue(userService.isEmailNotUnique("test@gmail.com"));
        assertFalse(userService.isEmailNotUnique("abc@gmail.com"));
    }

    @Test
    void createUser() {
        User testUser = new User();
        testUser.setFirstName("TestName");
        testUser.setLastName("TestSurname");
        User createdUser = userService.createUser(testUser);

        assertEquals(testUser.getFirstName(), createdUser.getFirstName());
        assertEquals(testUser.getLastName(), createdUser.getLastName());
    }

    @Test
    void getUserById() {
        User user = userService.getUserById(1L);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(5L));
    }

    @Test
    void deleteUserById() {
        assertTrue(userService.deleteUserById(1L));
        assertFalse(userService.deleteUserById(5L));
    }

    @Test
    void patchUserById() {
        User user = new User();
        user.setEmail("newtest@gmail.com");
        user.setFirstName("");

        User patchedUser = userService.patchUserById(1L, user);

        assertEquals(user.getEmail(), patchedUser.getEmail());
        assertNotEquals(user.getFirstName(), patchedUser.getFirstName());
    }

    @Test
    void updateUserById() {
        User user = new User();
        user.setFirstName("UpdatedName");
        user.setLastName("UpdatedSurname");
        user.setEmail("updated@gmail.com");
        user.setBirthDate(LocalDate.of(2005, 05, 10));

        User updatedUser = userService.updateUserById(1L, user);

        assertNotSame(user, updatedUser);
        assertEquals(1L, updatedUser.getId());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getBirthDate(), updatedUser.getBirthDate());
    }

    @Test
    void getUsersByBirthDateRange() {
        LocalDate from1 = LocalDate.of(1990, 1, 1);
        LocalDate to1 = LocalDate.of(1999, 12, 31);

        LocalDate from2 = LocalDate.of(2000, 1, 1);
        LocalDate to2 = LocalDate.of(2009, 12, 31);

        List<User> result1 = userService.getUsersByBirthDateRange(from1, to1);
        List<User> result2 = userService.getUsersByBirthDateRange(from2, to2);

        assertTrue(result1.isEmpty());
        assertTrue(result2.size() == 1);
    }
}