package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.ReviewResponse;
import com.app.rateuniversityapplicationapi.entity.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IReviewService {

    List<ReviewResponse> getAllReviews();

    ResponseEntity<ReviewResponse> saveReview(ReviewRequest review);

    List<ReviewResponse> getReviewsByUserId(UUID userId);

    List<ReviewResponse> getReviewsByCourseId(UUID uuid);
}
