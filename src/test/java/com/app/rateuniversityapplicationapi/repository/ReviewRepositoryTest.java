package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    IReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void findReviewByUserEmail() {

//        ReviewRequest reviewRequest = ReviewRequest.builder()
//                .courseId("02a88816-3fb4-4cb3-bb9c-7dde41c10a46")
//                .email("masimo_ramaj@shqiptar.eu")
//                .message("TESTIN Course repo")
//                .rating(4)
//                .build();
//
//        reviewService.saveReview(reviewRequest);
//
//        List<ReviewResponse> reviewResponses = reviewService
//                .getReviewsByUserEmail("masimo_ramaj@shqiptar.eu");
//
//        System.out.println(reviewResponses);
    }
}