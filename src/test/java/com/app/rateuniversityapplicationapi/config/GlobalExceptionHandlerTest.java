package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.exceptions.CourseNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.LecturerNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.ReviewNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RestController
class TestController {
    @GetMapping("/testException")
    public void testException() throws Exception {
        throw new Exception("General error");
    }

    @GetMapping("/testCourseNotFound")
    public void testCourseNotFoundException() throws CourseNotFoundException {
        throw new CourseNotFoundException("Course not found");
    }

    @GetMapping("/testUserNotFound")
    public void testUserNotFoundException() throws UserNotFoundException {
        throw new UserNotFoundException("User not found");
    }

    @GetMapping("/testReviewNotFound")
    public void testReviewNotFoundException() throws ReviewNotFoundException {
        throw new ReviewNotFoundException("Review not found");
    }

    @GetMapping("/testLecturerNotFound")
    public void testLecturerNotFoundException() throws LecturerNotFoundException {
        throw new LecturerNotFoundException("Lecturer not found");
    }
}

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGlobalException() throws Exception {
        mockMvc.perform(get("/testException").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("General error"));
    }

    @Test
    void testCourseNotFoundException() throws Exception {
        mockMvc.perform(get("/testCourseNotFound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course not found"));
    }

    @Test
    void testLecturerNotFoundException() throws Exception {
        mockMvc.perform(get("/testLecturerNotFound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Lecturer not found"));
    }

    @Test
    void testUserNotFoundException() throws Exception {
        mockMvc.perform(get("/testUserNotFound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not found"));
    }

    // Test for ReviewNotFoundException
    @Test
    void testReviewNotFoundException() throws Exception {
        mockMvc.perform(get("/testReviewNotFound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Review not found"));
    }
}
