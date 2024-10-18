package com.medhead.usersmicroservice.services;

import com.medhead.usersmicroservice.Dtos.RegisterUserDto;
import com.medhead.usersmicroservice.Entities.Role;
import com.medhead.usersmicroservice.Entities.RoleEnum;
import com.medhead.usersmicroservice.Entities.User;
import com.medhead.usersmicroservice.Repositories.RoleRepository;
import com.medhead.usersmicroservice.Repositories.UserRepository;
import com.medhead.usersmicroservice.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        expectedUsers.add(new User());

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.allUsers();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createAdministrator_WithValidRole_ShouldReturnSavedUser() {
        // Arrange
        RegisterUserDto input = new RegisterUserDto();
        input.setFullName("Test User");
        input.setEmail("test@example.com");
        input.setPassword("password");

        Role role = new Role();
        role.setName(RoleEnum.ADMIN);

        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(role));

        User expectedUser = new User();
        expectedUser.setFullName(input.getFullName());
        expectedUser.setEmail(input.getEmail());
        expectedUser.setPassword("encoded_password");
        expectedUser.setRole(role);

        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User actualUser = userService.createAdministrator(input);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.getFullName(), actualUser.getFullName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getRole(), actualUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createAdministrator_WithInvalidRole_ShouldReturnNull() {
        // Arrange
        RegisterUserDto input = new RegisterUserDto();
        input.setFullName("Test User");
        input.setEmail("test@example.com");
        input.setPassword("password");

        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.empty());

        // Act
        User actualUser = userService.createAdministrator(input);

        // Assert
        assertNull(actualUser);
        verify(userRepository, never()).save(any(User.class));
    }
}