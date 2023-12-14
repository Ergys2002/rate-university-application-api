package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService{

    private final CourseRepository courseRepository;

    @Override
    public List<Course> findAllByPageNumber(int page) {
        Pageable pageRequest = PageRequest.of(page,6);

        return courseRepository.findAll(pageRequest)
                .getContent();
    }

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
    public Course getCourseById(UUID courseId) {
        return courseRepository.getCourseById(courseId);
    }

    @Override
    public int getNumberOfCourses() {
        return courseRepository.getNumberOfCourses();
    }

}
