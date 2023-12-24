package com.app.rateuniversityapplicationapi.service.classes;


import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Review;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.exceptions.CourseNotFoundException;
import com.app.rateuniversityapplicationapi.exceptions.ReviewNotFoundException;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.ReviewRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

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
                    findByEmail(review.getEmail());

        Course courseFromDB = courseRepository
                .findById(UUID.fromString(review.getCourseId()))
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course with id: " + review.getCourseId() + "not found!"
                ));

        //calculate avg rating
        int avgRating = (int) ((courseFromDB.getCourseRating() + review.getRating()) / 2);
        //mqns i kemi shtuar reviewt fillestare me -1
        if (avgRating<0){avgRating = avgRating * (-1);}
        Review toBeSaved = Review.builder()
                .course(courseFromDB)
                .user(userFromDB)
                .courseReview(review.getMessage())
                .rating(avgRating)
                .build();

        UUID reviewID = reviewRepository.save(toBeSaved).getId();

        Review savedReview = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new ReviewNotFoundException("Review is not saved"));

        return new ResponseEntity<>( convertReviewToReviewResponse(savedReview), HttpStatus.ACCEPTED);
    }

    @Override
    public List<ReviewResponse> getReviewsByUserEmail(String email) {

        return reviewRepository.findReviewsByUserEmail(email)
                .stream().map(this::convertReviewToReviewResponse)
                .collect(Collectors.toList());

    }

    @Override
    public List<ReviewResponse> getReviewsByCourseId(UUID courseId) {
        return reviewRepository.findReviewsByCourseUUID(courseId)
                .stream().map(this::convertReviewToReviewResponse)
                .collect(Collectors.toList());
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

    public void deleteOldReviews() {
        List<Review> oldReviews = reviewRepository.findByCreatedAtBefore(LocalDate.now().minusDays(1));
        reviewRepository.deleteAll(oldReviews);
    }
}
