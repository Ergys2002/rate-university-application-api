package com.app.rateuniversityapplicationapi.dto.responses;

import java.time.LocalDate;

public interface CourseResponse {
    String getId();
    String getTitle();
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();
    boolean isAvailable();
    int getTotalQuotes();
    int getEnrolledStudents();
    double getRating();
    String getLecturerId();
    String getPicture();

}
