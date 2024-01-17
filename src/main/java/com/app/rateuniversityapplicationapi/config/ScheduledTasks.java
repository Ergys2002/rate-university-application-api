package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {


    private final IReviewService reviewService;

    @Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds
    public void deleteOldReviewsTask() {
        reviewService.deleteOldReviews();
    }
}
