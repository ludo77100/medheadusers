/*
package com.medhead.usersmicroservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import com.medhead.usersmicroservice.repositories.UserRepository;
import com.medhead.usersmicroservice.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository ;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtTokenUser;
    private String jwtTokenAdmin;
    private User user;
    private User adminUser;

    @BeforeEach
    void setUp() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Role userRole = roleRepository.findByName(RoleEnum.USER).get();
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).get();

        user = new User();
        user.setEmail("authenticateduser@mail.com");
        user.setPassword(encoder.encode("password123"));
        user.setFullName("Authenticated User");
        user.setRole(userRole);
        user = userRepository.save(user);

        jwtTokenUser = "Bearer " + jwtService.generateToken(user);

        adminUser = new User();
        adminUser.setEmail("adminuser@mail.com");
        adminUser.setPassword(encoder.encode("adminpassword"));
        adminUser.setFullName("Admin User");
        adminUser.setRole(adminRole);
        adminUser = userRepository.save(adminUser);

        jwtTokenAdmin = "Bearer " + jwtService.generateToken(adminUser);
    }

    @Test
    void testAuthenticatedUser_Success() throws Exception {

        mockMvc.perform(get("/users/me")
                        .header("Authorization", jwtTokenUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("authenticateduser@mail.com"))
                .andExpect(jsonPath("$.fullName").value("Authenticated User"));
    }

    @Test
    void testGetAllUsers_AdminRole_Success() throws Exception {
        // Performing the GET request to /users with admin authentication
        mockMvc.perform(get("/users")
                        .header("Authorization", jwtTokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }
}*/
