package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppConfigTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appConfig = new AppConfig(userRepository);
    }

    @Test
    void authenticationManagerBean() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        AuthenticationManager authenticationManager = appConfig.authenticationManager(authenticationConfiguration);

        assertNotNull(authenticationManager);
    }

    @Test
    void userDetailsServiceBean() {
        UserDetailsService userDetailsService = appConfig.userDetailsService();

        assertNotNull(userDetailsService);
        // Additional checks can be performed here depending on the behavior of your UserDetailsService
    }

    @Test
    void authenticationProviderBean() {
        AuthenticationProvider authenticationProvider = appConfig.authenticationProvider();

        assertNotNull(authenticationProvider);
        // Additional checks like checking if the correct UserDetailsService and PasswordEncoder are set can be done here
    }

    @Test
    void passwordEncoderBean() {
        PasswordEncoder passwordEncoder = appConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}
