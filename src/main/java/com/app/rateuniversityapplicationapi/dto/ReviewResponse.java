package com.app.rateuniversityapplicationapi.dto;

import java.time.LocalDate;

public interface ReviewResponse {
    String getId();
    String getMessage();
    LocalDate getCreatedAt();
    double getRating();
}
