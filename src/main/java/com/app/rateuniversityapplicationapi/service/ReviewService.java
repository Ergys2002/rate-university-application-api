package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.ReviewResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Review;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.exceptions.CourseNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.ReviewNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.UserNotFoundException;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.ReviewRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    @Override
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertReviewToReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ReviewResponse> saveReview(ReviewRequest review) {
        User userFromDB = userRepository.
                    findById(UUID.fromString(review.getUserId()))
                    .orElseThrow(() -> new  UserNotFoundException("User with id :" + review.getUserId() + "not found"));

        Course courseFromDB = courseRepository
                .findById(UUID.fromString(review.getCourseId()))
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course with id: " + review.getCourseId() + "not found!"
                ));

        Review toBeSaved = Review.builder()
                .course(courseFromDB)
                .user(userFromDB)
                .courseReview(review.getMessage())
                .rating(review.getRating())
                .build();

        UUID reviewID = reviewRepository.save(toBeSaved).getId();

        Review savedReview = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new ReviewNotFoundException("Review is not saved"));

        return new ResponseEntity<>( convertReviewToReviewResponse(savedReview), HttpStatus.ACCEPTED);
    }

    @Override
    public List<ReviewResponse> getReviewsByUserId(UUID userId) {
        return reviewRepository.findReviewByUserUUID(userId);
    }

    private ReviewResponse convertReviewToReviewResponse(Review review){
        return new ReviewResponse() {
            @Override
            public String getId() {
                return review.getId().toString();
            }

            @Override
            public String getMessage() {
                return review.getCourseReview();
            }

            @Override
            public LocalDate getCreatedAt() {
                return review.getCreatedAt();
            }

            @Override
            public double getRating() {
                return review.getRating();
            }
        };
    }
}
