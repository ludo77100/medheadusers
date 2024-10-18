package com.medhead.usersmicroservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.usersmicroservice.Dtos.LoginUserDto;
import com.medhead.usersmicroservice.Dtos.RegisterUserDto;
import com.medhead.usersmicroservice.Entities.User;
import com.medhead.usersmicroservice.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;

    @BeforeEach
    public void setUp() {

        registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("testuser@mail.com");
        registerUserDto.setPassword("password123");
        registerUserDto.setFullName("Test User");

        loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("testuser@mail.com");
        loginUserDto.setPassword("password123");
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@mail.com"))
                .andExpect(jsonPath("$.fullName").value("Test User"));

        User user = userRepository.findByEmail("testuser@mail.com").orElse(null);

        assertThat(user).isNotNull();
        assertThat(user.getFullName()).isEqualTo("Test User");
    }


    @Test
    public void testAuthenticateUser_Success() throws Exception {

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNotEmpty());

        User user = userRepository.findByEmail("testuser@mail.com").orElse(null);

        assertThat(user).isNotNull();
    }

}
