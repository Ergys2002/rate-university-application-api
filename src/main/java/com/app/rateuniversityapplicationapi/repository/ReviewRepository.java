package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query(value = "SELECT u FROM Review u WHERE u.user.id = ?1")
    List<ReviewResponse> findReviewByUserUUID(UUID uuid);

    @Query(value = "SELECT u FROM Review u WHERE u.course.id = ?1 ORDER BY u.createdAt desc ")
    List<Review> findReviewsByCourseUUID(UUID uuid);

    @Query(value = "SELECT u FROM Review u WHERE u.user.email = ?1")
    List<Review> findReviewsByUserEmail(String email);

    List<Review> findByCreatedAtBefore(LocalDate createdAt);
}
