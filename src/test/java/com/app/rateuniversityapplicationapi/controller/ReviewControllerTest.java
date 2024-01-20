package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReviewControllerTest {

    @Mock
    private IReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        objectMapper = new ObjectMapper();
    }

    private ReviewResponse createMockReviewResponse(String id, String message, double rating) {
        Course course = new Course(); // Replace with actual Course object creation
        return new ReviewResponse() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public LocalDate getCreatedAt() {
                return LocalDate.now();
            }

            @Override
            public double getRating() {
                return rating;
            }

            @Override
            public Course getCourse() {
                return course;
            }
        };
    }

    @Test
    void getAllReviewsTest() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(
                createMockReviewResponse("1", "Great course!", 5.0),
                createMockReviewResponse("2", "Good content", 4.0)
        );

        when(reviewService.getAllReviews()).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getReviewByUserEmailTest() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(
                createMockReviewResponse("1", "Insightful course", 4.5),
                createMockReviewResponse("2", "Very informative", 4.0)
        );

        when(reviewService.getReviewsByUserEmail()).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews/user-reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getReviewByCourseIdTest() throws Exception {
        String courseId = UUID.randomUUID().toString();
        List<ReviewResponse> reviews = Arrays.asList(
                createMockReviewResponse("3", "Challenging but rewarding", 5.0),
                createMockReviewResponse("4", "Requires dedication", 4.5)
        );

        when(reviewService.getReviewsByCourseId(UUID.fromString(courseId))).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews/course-reviews/" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAverageRatingTest() throws Exception {
        String courseId = UUID.randomUUID().toString();
        int averageRating = 4;

        when(reviewService.getAverageRating(UUID.fromString(courseId))).thenReturn(averageRating);

        mockMvc.perform(get("/api/reviews/get-average-rating/" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(averageRating)));
    }

    @Test
    void saveReviewTest() throws Exception {
        ReviewRequest request = new ReviewRequest("user@example.com", UUID.randomUUID().toString(), "Excellent", 5.0);
        ReviewResponse response = createMockReviewResponse("5", "Excellent", 5.0);

        when(reviewService.saveReview(any(ReviewRequest.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        mockMvc.perform(post("/api/reviews/save-review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Similar implementations for other tests...
}
