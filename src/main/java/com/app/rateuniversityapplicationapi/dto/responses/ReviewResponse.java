package com.app.rateuniversityapplicationapi.dto.responses;

import java.time.LocalDate;
import com.app.rateuniversityapplicationapi.entity.Course;

public interface ReviewResponse {
    String getId();
    String getMessage();
    LocalDate getCreatedAt();
    double getRating();
    Course getCourse();
}
