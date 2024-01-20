package com.app.rateuniversityapplicationapi.service.classes;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Review;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.ReviewRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set up mock data here
    }

    @Test
    void testGetAllReviews() {
        List<Review> mockReviews = createMockReviews();
        when(reviewRepository.findAll()).thenReturn(mockReviews);

        List<ReviewResponse> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(mockReviews.size(), result.size());
        // Additional assertions based on ReviewResponse properties
        verify(reviewRepository).findAll();
    }

    @Test
    void testSaveReview_Success() {
        String validCourseId = UUID.randomUUID().toString();
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .courseId(validCourseId)
                .email("user@example.com")
                .message("Great course")
                .rating(5.0)
                .build();

        // Create a mocked course with initialized registered students and reviews
        Set<User> registeredStudents = new HashSet<>();
        registeredStudents.add(User.builder().email(reviewRequest.getEmail()).build()); // Add the user who is submitting the review

        List<Review> reviews = new ArrayList<>(); // Initialize the reviews list

        Course course = Course.builder()
                .id(UUID.fromString(validCourseId))
                .title("Sample Course")
                .registeredStudents(registeredStudents)
                .reviews(reviews) // Set the reviews
                .build();

        User user = User.builder()
                .email(reviewRequest.getEmail())
                .build();

        Review savedReview = Review.builder()
                .course(course)
                .user(user)
                .courseReview(reviewRequest.getMessage())
                .rating(reviewRequest.getRating())
                .build();

        when(courseRepository.findById(UUID.fromString(reviewRequest.getCourseId()))).thenReturn(Optional.of(course));
        when(userRepository.findByEmail(reviewRequest.getEmail())).thenReturn(user);
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview); // Return the saved review
        when(reviewRepository.findById(savedReview.getId())).thenReturn(Optional.of(savedReview)); // Return the saved review on findById call

        ResponseEntity<ReviewResponse> response = reviewService.saveReview(reviewRequest);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        // Additional assertions for ReviewResponse properties
        verify(courseRepository).findById(UUID.fromString(reviewRequest.getCourseId()));
        verify(userRepository).findByEmail(reviewRequest.getEmail());
        verify(reviewRepository).save(any(Review.class));
        verify(reviewRepository).findById(savedReview.getId());
    }

    @Test
    void testGetReviewsByUserEmail() {
        // Arrange
        String userEmail = "user@example.com";
        List<Review> mockReviews = createMockReviewsForUser(userEmail);
        when(reviewRepository.findReviewsByUserEmail(userEmail)).thenReturn(mockReviews);

        UserResponse currentUser = new UserResponse() {
            @Override
            public String getFirstName() {
                return null;
            }

            @Override
            public String getLastName() {
                return null;
            }

            @Override
            public String getEmail() {
                return userEmail;
            }

            @Override
            public LocalDate getBirthDate() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public String getProfilePhotoUrl() {
                return null;
            }
        }; // Mock UserResponse

        when(userService.getCurrentUser()).thenReturn(currentUser);

        // Act
        List<ReviewResponse> result = reviewService.getReviewsByUserEmail();

        // Assert
        assertNotNull(result);
        assertEquals(mockReviews.size(), result.size());
        for (int i = 0; i < mockReviews.size(); i++) {
            ReviewResponse reviewResponse = result.get(i);
            Review review = mockReviews.get(i);
            assertEquals(review.getId().toString(), reviewResponse.getId());
            assertEquals(review.getCourseReview(), reviewResponse.getMessage());
            // Add more assertions as needed
        }

        // Verify
        verify(reviewRepository).findReviewsByUserEmail(userEmail);
    }


    private List<Review> createMockReviewsForUser(String userEmail) {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Assume 3 reviews for simplicity
            Course course = new Course(); // Initialize with appropriate values
            User user = new User(); // Initialize user with userEmail
            user.setEmail(userEmail);
            Review review = Review.builder()
                    .id(UUID.randomUUID())
                    .courseReview("Review " + i)
                    .rating(4.0 + i)
                    .course(course)
                    .user(user)
                    .createdAt(LocalDate.now())
                    .build();
            reviews.add(review);
        }
        return reviews;
    }



    @Test
    void testGetReviewsByCourseId() {
        UUID courseId = UUID.randomUUID();
        List<Review> mockReviews = createMockReviews();
        when(reviewRepository.findReviewsByCourseUUID(courseId)).thenReturn(mockReviews);

        List<ReviewResponse> result = reviewService.getReviewsByCourseId(courseId);

        assertNotNull(result);
        assertEquals(mockReviews.size(), result.size());
        // Additional assertions for ReviewResponse properties
        verify(reviewRepository).findReviewsByCourseUUID(courseId);
    }

    @Test
    void testGetAverageRating() {
        UUID courseId = UUID.randomUUID();
        List<Review> mockReviews = createMockReviews();
        Course course = createMockCourse();

        when(courseRepository.getCourseById(courseId)).thenReturn(course);
        when(reviewRepository.findReviewsByCourseUUID(courseId)).thenReturn(mockReviews);

        int averageRating = reviewService.getAverageRating(courseId);

        // Assert based on the expected average rating calculation
        verify(courseRepository).getCourseById(courseId);
        verify(reviewRepository).findReviewsByCourseUUID(courseId);
    }

    // Additional private methods for creating mock data
    private ReviewRequest createMockReviewRequest() {
        return new ReviewRequest("user@example.com", "course-id", "Great course!", 5.0);
    }

    private Course createMockCourse() {
        return Course.builder()
                .id(UUID.randomUUID())
                .title("Sample Course")
                .isAvailable(true)
                // Add other necessary fields
                .build();
    }

    private UserResponse createMockUser() {
        return new UserResponse() {
            @Override
            public String getFirstName() {
                return "example";
            }

            @Override
            public String getLastName() {
                return "dasdas";
            }

            @Override
            public String getEmail() {
                return "dasdsad";
            }

            @Override
            public LocalDate getBirthDate() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public String getProfilePhotoUrl() {
                return null;
            }
        };
    }

    private Review createMockReview() {
        return Review.builder()
                .id(UUID.randomUUID())
                .courseReview("Great course!")
                .rating(5.0)
                .createdAt(LocalDate.now())
                // Add other necessary fields
                .build();
    }

    private List<Review> createMockReviews() {
        return Collections.singletonList(createMockReview());
    }

    @Test
    void testDeleteOldReviews() {
        // Arrange
        List<Review> oldReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            oldReviews.add(Review.builder()
                    .id(UUID.randomUUID())
                    .courseReview("Review " + i)
                    // other properties of Review
                    .build());
        }

        LocalDate cutoffDate = LocalDate.now().minusDays(1);
        when(reviewRepository.findByCreatedAtBefore(cutoffDate)).thenReturn(oldReviews);

        // Act
        reviewService.deleteOldReviews();

        // Assert
        verify(reviewRepository).findByCreatedAtBefore(cutoffDate);
        verify(reviewRepository).deleteAll(oldReviews);
    }
}
