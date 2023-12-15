package com.app.rateuniversityapplicationapi.dto;

import java.time.LocalDate;

public interface CourseResponse {
    String getId();
    String getTitle();
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();
    boolean isAvailable();
    int getTotalQuotes();
    int getFreeQuotes();
    double getRating();
    String getLecturerId();

}
