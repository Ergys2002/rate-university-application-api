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

        Course courseFromDB = courseRepository
                .findById(UUID.fromString(review.getCourseId()))
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course with id: " + review.getCourseId() + "not found!"
                ));

        boolean isUserEnrolled = courseFromDB
                .getRegisteredStudents()
                .stream()
                .anyMatch(s -> s.getEmail().equals(review.getEmail()));

        if (!isUserEnrolled){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        boolean hasReviewWithTheSameEmail = courseFromDB.getReviews().stream()
                .anyMatch(r -> r.getUser().getEmail().equals(review.getEmail()));

        if (hasReviewWithTheSameEmail){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User userFromDB = userRepository.
                findByEmail(review.getEmail());



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

    @Override
    public int getAverageRating(UUID uuid) {
        List<ReviewResponse> reviews = this.getReviewsByCourseId(
                uuid
        );

        int count = reviews.size();

        if (count == 0){
            return 0;
        }

        double averageRating = reviews.stream()
                .mapToDouble(ReviewResponse::getRating)
                .average()
                .orElse(0.0);

        Course course = this.courseRepository.getCourseById(uuid);
        course.setCourseRating(averageRating);
        this.courseRepository.save(course);

        return (int) (averageRating);
    }
}
