package com.app.rateuniversityapplicationapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void corsConfigurationSource() {
        CorsConfig corsConfig = new CorsConfig();
        CorsConfigurationSource corsConfigurationSource = corsConfig.corsConfigurationSource();

        assertNotNull(corsConfigurationSource);
        assertTrue(corsConfigurationSource instanceof UrlBasedCorsConfigurationSource);

        UrlBasedCorsConfigurationSource source = (UrlBasedCorsConfigurationSource) corsConfigurationSource;
        CorsConfiguration configuration = source.getCorsConfigurations().get("/**");

        assertNotNull(configuration);
        assertEquals(List.of("*"), configuration.getAllowedOriginPatterns());
        assertEquals(List.of("*"), configuration.getAllowedMethods());
        assertEquals(List.of("*"), configuration.getAllowedHeaders());
    }
}
