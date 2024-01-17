package com.app.rateuniversityapplicationapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/students/number-of-students").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/lecturers/number-of-lecturers").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/courses/number-of-courses").permitAll()
                        .requestMatchers(HttpMethod.GET,"/img/lecturers/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/img/courses/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/img/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

