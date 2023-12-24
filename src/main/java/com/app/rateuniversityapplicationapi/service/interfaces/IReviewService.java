package com.app.rateuniversityapplicationapi.service.interfaces;

import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IReviewService {

    List<ReviewResponse> getAllReviews();

    ResponseEntity<ReviewResponse> saveReview(ReviewRequest review);

    List<ReviewResponse> getReviewsByUserEmail(String email);

    List<ReviewResponse> getReviewsByCourseId(UUID uuid);

    void deleteOldReviews();

    int getAverageRating(UUID uuid);
}
