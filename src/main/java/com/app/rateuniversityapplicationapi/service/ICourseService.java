package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.entity.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    List<Course> findAllByPageNumber(int page);

    List<Course> getAllCourses();

    List<Course> getCoursesByName(String name);

    List<Course> getAllAvailableCourses();

    List<Course> getTopTenRatedCourses();

   CourseResponse getCourseById(UUID courseId);

    int getNumberOfCourses();
}
