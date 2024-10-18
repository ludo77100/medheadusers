package com.medhead.usersmicroservice.services;

import com.medhead.usersmicroservice.Services.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private Key signingKey;

    @BeforeEach
    void setUp() {
        jwtService.secretKey = Base64.getEncoder().encodeToString("48581478eda919e9ab36951c59d10c5ae534c08f70b12d239b11da1d122ab376cda897fca6af3292675c026008e7e0e5de34e6dae53bfc685f8f520f46513427a64d575de453fcd493ad4dad5d47ea6b7e66acff23962d4a49e774adb5d163bb391cee4665f9df7f9a066b0a4de10fd782111e10cae0e8256ba9156d34051f0bfb61dbd58ddc886f1a1b19e5efc4f3869daf7257afd0466d6a329deeeb9cd3ac43875e76bbfbda724b743f310f34e2baffbd4e9c2c7b296c71eaa426e44eed50ac2915b03714395e7024a21c3b2d613ee6661409d34106b14043511c9ba75c6f2e473e6395439bafec486a6349d068b220eeda5e571555d336a4d6e816c932fc".getBytes());
        jwtService.jwtExpiration = 1000 * 60 * 60; // 1 heure
    }

    @Test
    public void testExtractUsername() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testIsTokenInvalidWithIncorrectUsername() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUserDetails = new User("anotherUser", "password", userDetails.getAuthorities());
        assertFalse(jwtService.isTokenValid(token, differentUserDetails));
    }

    @Test
    public void testExtractClaim() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);
        assertEquals(userDetails.getUsername(), claims.getSubject());
    }

    private UserDetails createUserDetails() {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new User("testUser", "password", authorities);
    }
}