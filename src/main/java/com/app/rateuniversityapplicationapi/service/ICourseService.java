package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.StudentResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ICourseService {

    boolean isEnrolled(UUID courseId, String email);

    void appendUser(String userEmail, UUID courseId);

    List<Course> findAllByPageNumber(int page);

    List<Course> getAllCourses();

    List<Course> getCoursesByName(String name);

    List<Course> getAllAvailableCourses();

    List<Course> getTopTenRatedCourses();

   CourseResponse getCourseById(UUID courseId);

   List<CourseResponse> getAllCoursesOfALecturer(String id);

    int getNumberOfCourses();

    List<StudentResponse> getUsersByEnrolledCourseContains(UUID courseUUID);
}
