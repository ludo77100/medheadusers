package com.medhead.usersmicroservice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.usersmicroservice.Dtos.RegisterUserDto;
import com.medhead.usersmicroservice.Entities.User;
import com.medhead.usersmicroservice.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "SUPER_ADMIN")
class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterUserDto registerUserDto;

    @BeforeEach
    void setUp() {

        registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("newadmin@mail.com");
        registerUserDto.setPassword("password123");
        registerUserDto.setFullName("Admin");

    }

    @Test
    void shouldCreateAdministrator() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(registerUserDto);

        mockMvc.perform(post("/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newadmin@mail.com")))
                .andExpect(jsonPath("$.role.name", is("ADMIN")));
    }
}
