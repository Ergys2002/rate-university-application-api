package com.app.rateuniversityapplicationapi.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        userDetails = new User("test@example.com", "password", new ArrayList<>());
    }

    @Test
    void generateTokenTest() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsernameTest() {
        String token = jwtService.generateToken(userDetails);
        assertEquals("test@example.com", jwtService.extractUsername(token));
    }

    @Test
    void tokenExpirationTest() {
        String token = jwtService.generateToken(userDetails);
        assertFalse(jwtService.isTokenExpired(token));
    }


    @Test
    void validateTokenTest() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void validateTokenWithDifferentUserTest() {
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = new User("other@example.com", "password", new ArrayList<>());
        assertFalse(jwtService.isTokenValid(token, differentUser));
    }

    @Test
    void extractClaimTest() {
        String token = jwtService.generateToken(userDetails);
        Date issuedAt = jwtService.extractClaim(token, claims -> claims.getIssuedAt());
        assertNotNull(issuedAt);
    }

    // Additional tests can be added for other specific claim checks or error cases
}
