package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Review;
import com.app.rateuniversityapplicationapi.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByUserId(UUID userId) {
        return reviewRepository.findReviewByUserUUID(userId);
    }
}
