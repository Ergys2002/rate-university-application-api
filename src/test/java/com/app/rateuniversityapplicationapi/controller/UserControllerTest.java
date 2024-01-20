package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.requests.*;
import com.app.rateuniversityapplicationapi.dto.responses.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




class UserControllerTest {


    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()); // ObjectMapper with JavaTimeModule
    }

    @Test
    void registerTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john@example.com", "password", LocalDate.now(), "1234567890");
        AuthenticationResponse authResponse = new AuthenticationResponse("User Registered", "token", LocalDate.now());
        when(userService.register(registerRequest)).thenReturn(ResponseEntity.ok(authResponse));

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))) // Using the configured objectMapper
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User Registered"));
    }
    @Test
    void authenticateTest() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("user@example.com", "password");
        AuthenticationResponse authResponse = new AuthenticationResponse("Welcome", "token", LocalDate.now());
        when(userService.authenticate(authRequest)).thenReturn(ResponseEntity.ok(authResponse));

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }

    // Test for the getNumberOfStudents method
    @Test
    void getNumberOfStudentsTest() throws Exception {
        when(userService.getNumberOfStudents()).thenReturn(10);

        mockMvc.perform(get("/api/students/number-of-students"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    // Test for the getLoggedInUser method
    @Test
    void getLoggedInUserTest() throws Exception {
        UserResponse userResponse = mock(UserResponse.class);
        when(userService.getCurrentUser()).thenReturn(userResponse);

        mockMvc.perform(get("/api/user/logged-in-user"))
                .andExpect(status().isOk());
    }

    // Test for the updateUserProfile method
    @Test
    void updateUserProfileTest() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("Jane", "Doe", "newpass", "1234567890", null);
        AuthenticationResponse authResponse = new AuthenticationResponse("Profile Updated", "newToken", LocalDate.now());
        when(userService.updateUser(any(UpdateUserRequest.class))).thenReturn(ResponseEntity.ok(authResponse));

        MockMultipartFile profilePhoto = new MockMultipartFile("profilePhoto", "photo.jpg", "image/jpeg", "test photo".getBytes());

        mockMvc.perform(put("/api/user/update-profile") // Change to put(...)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Profile Updated"));

    }

    @Test
    void dropCourseTest() throws Exception {
        EnrollRequest enrollRequest = new EnrollRequest("user@example.com", "51fd6f17-7de7-43fc-a812-ed81a6629fbd");

        // Perform the POST request
        mockMvc.perform(post("/api/drop-course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollRequest)))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct parameters
        verify(userService, times(1)).dropCourse(UUID.fromString(enrollRequest.getCourseId()), enrollRequest.getEmail());
    }


    // ... additional test methods for other endpoints ...
}
