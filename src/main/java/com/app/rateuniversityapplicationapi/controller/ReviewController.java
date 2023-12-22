package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.ReviewRequest;
import com.app.rateuniversityapplicationapi.dto.ReviewResponse;
import com.app.rateuniversityapplicationapi.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
   //prove
    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/u/{email}")
    public ResponseEntity<List<ReviewResponse>> getReviewByUserEmail(@PathVariable String email){
        List<ReviewResponse> reviews = reviewService.getReviewsByUserEmail(
                email
        );
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @GetMapping("/c/{courseId}")
    public ResponseEntity<List<ReviewResponse>> getReviewByCourseId(@PathVariable String courseId){
        List<ReviewResponse> reviews = reviewService.getReviewsByCourseId(
                UUID.fromString(courseId)
        );
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @PostMapping("/save-review")
    public ResponseEntity<ReviewResponse> saveReview(@RequestBody ReviewRequest request){
        return reviewService.saveReview(request);
    }
}

