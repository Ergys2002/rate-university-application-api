package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService{

    private final CourseRepository courseRepository;

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
        return courseRepository.getTop10RatedCourses();
    }

    @Override
    public int getNumberOfCourses() {
        return courseRepository.getNumberOfCourses();
    }

}
