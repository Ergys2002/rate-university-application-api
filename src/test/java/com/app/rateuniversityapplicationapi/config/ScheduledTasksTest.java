package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.service.interfaces.IReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

class ScheduledTasksTest {

    @Mock
    private IReviewService reviewService;

    private ScheduledTasks scheduledTasks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduledTasks = new ScheduledTasks(reviewService);
    }

    @Test
    void deleteOldReviewsTaskTest() {
        // Call the scheduled method
        scheduledTasks.deleteOldReviewsTask();

        // Verify that the service method is called
        verify(reviewService).deleteOldReviews();
    }
}
