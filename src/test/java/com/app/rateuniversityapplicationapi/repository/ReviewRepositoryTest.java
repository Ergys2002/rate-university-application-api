//package com.app.rateuniversityapplicationapi.repository;
//
//import com.app.rateuniversityapplicationapi.entity.Review;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class ReviewRepositoryTest {
//
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Test
//    void findReviewByUserUUID() {
//        UUID uuid = UUID.randomUUID();
//        Review review = Review.builder()
//                .id(uuid)
//                .courseReview("ABC")
//                .build();
//        List<Review> reviews = reviewRepository.findReviewByUserUUID(uuid);
//        assertEquals(review,reviews.get(0));
//    }
//}