package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.classes.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testAppendUser() {
        // Create a mock Course and User
        UUID courseId = UUID.randomUUID();
        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        mockCourse.setRegisteredStudents(new HashSet<>());

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setEnrolledCourses(new HashSet<>());

        // Mock repository methods
        Mockito.when(courseRepository.getCourseById(courseId)).thenReturn(mockCourse);
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(mockUser);

        // Invoke the method
        courseService.appendUser("test@example.com", courseId);

        // Verify that save methods are called
        Mockito.verify(courseRepository, times(1)).save(mockCourse);
        Mockito.verify(userRepository, times(1)).save(mockUser);

        // Verify that user is added to the course and course is added to the user
        Set<User> registeredStudents = mockCourse.getRegisteredStudents();
        Set<Course> enrolledCourses = mockUser.getEnrolledCourses();

        Assertions.assertTrue(registeredStudents.contains(mockUser));
        Assertions.assertTrue(enrolledCourses.contains(mockCourse));
    }
}
