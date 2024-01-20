package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.config.JwtService;
import com.app.rateuniversityapplicationapi.dto.requests.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.requests.RegisterRequest;
import com.app.rateuniversityapplicationapi.dto.requests.UpdateUserRequest;
import com.app.rateuniversityapplicationapi.dto.responses.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Role;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.classes.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private JwtService jwtService;

    @Mock
    private Claims mockClaims;


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken");
        when(mockClaims.getExpiration()).thenReturn(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(jwtService.extractAllClaims(anyString())).thenReturn(mockClaims);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("john@example.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRegisterNewUser() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        // set properties for registerRequest

        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken"); // Mock token generation

        // Act
        ResponseEntity<AuthenticationResponse> response = userService.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody().getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAuthenticate() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("john@example.com", "password");
        when(userRepository.findByEmail(authRequest.getEmail())).thenReturn(new User());
        // Mock authenticationManager.authenticate call

        // Act
        ResponseEntity<AuthenticationResponse> response = userService.authenticate(authRequest);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody().getToken());
        // Additional assertions can be added here
    }

    @Test
    void testUpdateUser() {
        // Mock User
        String userEmail = "ergys@example.com";
        User mockUser = new User();
        mockUser.setEmail(userEmail);
        mockUser.setFirstname("John");
        mockUser.setLastname("Doe");
        // Set other necessary properties for mockUser

        when(userRepository.findByEmail(userEmail)).thenReturn(mockUser);

        // Mock UserResponse
        UserResponse mockUserResponse = new UserResponse() {
            @Override
            public String getFirstName() { return mockUser.getFirstname(); }
            @Override
            public String getLastName() { return mockUser.getLastname(); }
            @Override
            public String getEmail() { return mockUser.getEmail(); }

            @Override
            public LocalDate getBirthDate() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public String getProfilePhotoUrl() {
                return null;
            }
            // Implement other methods as necessary...
        };

        // Mock the SecurityContext and Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(userEmail);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock userRepository to return the mock user
        when(userRepository.findByEmail(userEmail)).thenReturn(mockUser);

        // Create and set up UpdateUserRequest
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                "NewFirstName", "NewLastName", "newpassword", "123456789", null);

        // Act
        ResponseEntity<AuthenticationResponse> response = userService.updateUser(updateUserRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        // Additional assertions as needed

        // Verify interactions
        verify(userRepository, atLeastOnce()).findByEmail("ergys@example.com");
        verify(passwordEncoder).encode("newpassword");
        // Additional verifications as needed
    }





    @Test
    void testGetCurrentUser() {
        String userEmail = "john@example.com";
        User mockUser = new User();
        mockUser.setEmail(userEmail);
        mockUser.setFirstname("John");
        mockUser.setLastname("Doe");

        when(userRepository.findByEmail(userEmail)).thenReturn(mockUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEmail);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserResponse result = userService.getCurrentUser();

        // Assert
        assertNull(result);
    }



    @Test
    void testDropCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        String userEmail = "john@example.com";
        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        User mockUser = new User();
        mockUser.setEmail(userEmail);
        HashSet<User> enrolledUsers = new HashSet<>();
        enrolledUsers.add(mockUser);
        mockCourse.setRegisteredStudents(enrolledUsers);
        when(courseRepository.getCourseById(courseId)).thenReturn(mockCourse);

        // Act
        userService.dropCourse(courseId, userEmail);

        // Assert
        assertTrue(mockCourse.getRegisteredStudents().isEmpty());
        verify(courseRepository).getCourseById(courseId);
        verify(courseRepository).save(mockCourse);
    }

    @Test
    void testGetNumberOfStudents() {
        // Arrange
        int mockStudentCount = 5;
        when(userRepository.getNumberOfStudents()).thenReturn(mockStudentCount);

        // Act
        int result = userService.getNumberOfStudents();

        // Assert
        assertEquals(mockStudentCount, result);
        verify(userRepository).getNumberOfStudents();
    }

    @Test
    void testFindUserById() {
        // Arrange
        UUID mockUserId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(mockUserId);
        // Set other properties for mockUser...

        when(userRepository.findUserById(mockUserId)).thenReturn(mockUser);

        // Act
        User result = userService.findUserById(mockUserId);

        // Assert
        assertNotNull(result);
        assertEquals(mockUserId, result.getId());
        verify(userRepository).findUserById(mockUserId);
    }


    @Test
    void testSaveProfileImage() {
        // Arrange
        User mockUser = new User();
        mockUser.setFirstname("John");
        mockUser.setLastname("Doe");

        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        // Act
        String result = userService.saveProfileImage(mockUser, mockFile);

        // Assert
        assertNotNull(result);
        assertTrue(result.endsWith(".jpg"));
        // Additional assertions to validate the file path or content
    }


}
