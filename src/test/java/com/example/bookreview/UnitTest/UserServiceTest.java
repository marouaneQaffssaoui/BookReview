package com.example.bookreview.UnitTest;

import com.example.bookreview.model.User;
import com.example.bookreview.repository.UserRepository;
import com.example.bookreview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFname("John");
        user.setLname("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("1234");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.addUser(user);
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFname());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> found = userService.findUser(1L);
        assertNotNull(found.isPresent());
        assertEquals("JohnDoe@example.com", found.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
    }
}
