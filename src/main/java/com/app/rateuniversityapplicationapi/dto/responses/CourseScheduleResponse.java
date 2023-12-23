package com.app.rateuniversityapplicationapi.dto.responses;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface CourseScheduleResponse {
    String getId();

    String getLectureHall();

    String getCourseDate();

    String getCourseTime();

    double getDuration();

    @Value("#{target.course.title}")
    String getCourseTitle();
}
