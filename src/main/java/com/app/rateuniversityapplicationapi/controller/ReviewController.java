package com.app.rateuniversityapplicationapi.controller;


import com.app.rateuniversityapplicationapi.dto.requests.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.responses.ReviewResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user-reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewByUserEmail(){
        List<ReviewResponse> reviews = reviewService.getReviewsByUserEmail();
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @GetMapping("/course-reviews/{courseId}")
    public ResponseEntity<List<ReviewResponse>> getReviewByCourseId(@PathVariable String courseId){
        List<ReviewResponse> reviews = reviewService.getReviewsByCourseId(
                UUID.fromString(courseId)
        );
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @GetMapping("get-average-rating/{courseId}")
    public int getAverageRating(@PathVariable String courseId){
        return reviewService.getAverageRating(UUID.fromString(courseId));
    }

    @PostMapping("/save-review")
    public ResponseEntity<ReviewResponse> saveReview(@RequestBody ReviewRequest request){
        return reviewService.saveReview(request);
    }
}

