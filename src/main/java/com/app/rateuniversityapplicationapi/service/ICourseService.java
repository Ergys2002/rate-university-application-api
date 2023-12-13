package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    public List<Course> getAllCourses();

    public List<Course> getCoursesByName(String name);

    public List<Course> getAllAvailableCourses();

    public List<Course> getTopTenRatedCourses();

    public Course getCourseById(UUID courseId);

    int getNumberOfCourses();
}
