package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.CourseScheduleRepository;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitialDataPersistanceTest {

    @Mock
    private LecturerRepository lecturerRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseScheduleRepository courseScheduleRepository;

    private InitialDataPersistance initialDataPersistance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initialDataPersistance = new InitialDataPersistance(lecturerRepository, courseRepository, courseScheduleRepository);
    }


    @Test
    void createLecturerIfNotPresentTest() {
        // Set up
        when(lecturerRepository.getLecturersByEmail(anyString())).thenReturn(null);

        // Call method
        initialDataPersistance.createLecturerIfNotPresent("John", "Doe", "john@example.com", "profile.jpg", "Bio");

        // Assert and verify
        verify(lecturerRepository).save(any(Lecturer.class));
    }

    @Test
    void createCourseIfNotPresentTest() {
        // Set up
        when(courseRepository.getCourseByTitleEqualsIgnoreCaseAndDescriptionEqualsIgnoreCaseAndLecturerId(anyString(), anyString(), any(UUID.class))).thenReturn(null);

        // Call method
        initialDataPersistance.createCourseIfNotPresent("Course Title", "Course Description", LocalDate.now(), LocalDate.now().plusDays(30), true, 10, UUID.randomUUID(), "picture.jpg");

        // Assert and verify
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void createCourseScheduleIfNotPresentTest() {
        // Set up
        when(courseScheduleRepository.getCourseScheduleByCourseIdAndCourseDateAndCourseTimeAndDurationAndLectureHall(any(UUID.class), any(LocalDate.class), any(LocalTime.class), anyDouble(), anyString())).thenReturn(null);

        // Call method
        initialDataPersistance.createCourseScheduleIfNotPresent("Hall 1", LocalDate.now(), LocalTime.now(), 2, UUID.randomUUID());

        // Assert and verify
        verify(courseScheduleRepository).save(any(CourseSchedule.class));
    }
}
