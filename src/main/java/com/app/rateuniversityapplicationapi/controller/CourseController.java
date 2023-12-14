package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.service.CourseService;
import com.app.rateuniversityapplicationapi.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{course-name}")
    public List<Course> getCoursesByName(@PathVariable("course-name") String courseName){
        return courseService.getCoursesByName(courseName);
    }

    @GetMapping("/uuid/{courseId}")
    public Course getCoursesById(@PathVariable("courseId") String courseId){
        return courseService.getCourseById(UUID.fromString(courseId));
    }

    @GetMapping("page/{pageNumber}")
    public List<Course> getCourseByPageNumber(@PathVariable("pageNumber") int pageNumber){
        return courseService.findAllByPageNumber(pageNumber);
    }

    @GetMapping("/available-courses")
    public List<Course> getAllAvailableCourses(){
        return courseService.getAllAvailableCourses();
    }

    @GetMapping("/top-rated-courses")
    public List<Course> getTopRatedCourses(){
        return courseService.getTopTenRatedCourses();
    }

    @GetMapping("/number-of-courses")
    public int getNumberOfCourses(){
        return courseService.getNumberOfCourses();
    }

}
