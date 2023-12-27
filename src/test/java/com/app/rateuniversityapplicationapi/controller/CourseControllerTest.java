package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.service.classes.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private Course course;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("john.doe@example.com")
                .build();

        course = Course.builder()
                .title("Advanced Calculus")
                .description("Master the intricacies of advanced mathematical concepts and applications.")
                .totalQuotes(12)
                .enrolledStudents(1)
                .courseRating(5.0)
                .isAvailable(true)
                .build();

    }

    @Test
    void addStudent(){
        Mockito.when(
                courseService.appendUser(user.getEmail(),course.getId())
        ).thenReturn(course);
    }

    @Test
    void isEnrolled(){

    }

    @Test
    void getAllCourses() {

    }

    @Test
    void getAllAvailableCourses() {
    }

    @Test
    void getTopRatedCourses() {
    }

    @Test
    void getNumberOfCourses() {
    }

    @Test
    void getCoursesByName() {
    }

    @Test
    void getAllUsersByEnrolledCourse() {
    }
}