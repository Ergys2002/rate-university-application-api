package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.CompareCoursesByRateDesc;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CourseService implements ICourseService{
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.getCourseByCourseName(name);
    }

    @Override
    public List<Course> getAllAvailableCourses() {
        return courseRepository.getAllAvailableCourses(true);
    }

    @Override
    public List<Course> getTopTenRatedCourses() {
        List<Course> courses = courseRepository.findAll();
        List<Course> topTenCourses = new ArrayList<>();

        courses.sort(new CompareCoursesByRateDesc());

        int courseIndex = 0;

        while (!courses.isEmpty()){
            topTenCourses.add(courses.get(courseIndex));
            courseIndex++;
        }

        return topTenCourses;
    }


}
