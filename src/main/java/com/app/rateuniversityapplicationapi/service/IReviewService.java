package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Review;

import java.util.List;
import java.util.UUID;

public interface IReviewService {

    List<Review> getAllReviews();

    void saveReview(Review review);

    List<Review> getReviewsByUserId(UUID userId);

}
