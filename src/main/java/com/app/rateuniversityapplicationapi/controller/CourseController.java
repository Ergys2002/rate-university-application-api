package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.requests.EnrollRequest;
import com.app.rateuniversityapplicationapi.dto.responses.StudentResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.service.interfaces.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/isEnrolled")
    public boolean isEnrolled(@RequestBody EnrollRequest enrollRequest){
       return courseService.isEnrolled(
               UUID.fromString(enrollRequest.getCourseId()),
               enrollRequest.getEmail());
    }

    @GetMapping("/{course-name}")
    public List<Course> getCoursesByName(@PathVariable("course-name") String courseName){
        return courseService.getCoursesByName(courseName);
    }

    @GetMapping("/details")
    public CourseResponse getCoursesById(@RequestParam("id") UUID courseId){
        return courseService.getCourseById(courseId);
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

    @PostMapping(path = "/enroll",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addStudent(@RequestBody EnrollRequest enrollRequest){
        courseService.appendUser(
                enrollRequest.getEmail(),
                UUID.fromString(enrollRequest.getCourseId()));
    }

    @GetMapping("/enrolled-students/{courseUUID}")
    public List<StudentResponse> getAllUsersByEnrolledCourse(
            @PathVariable("courseUUID") String uuid){
        return courseService.getUsersByEnrolledCourseContains(UUID.fromString(uuid));
    }

    @GetMapping("/all-courses-of-a-lecturer")
    public List<CourseResponse> getAllCoursesOfAlecturer(@RequestParam("id") String id){

        return courseService.getAllCoursesOfALecturer(id);

    }

    @GetMapping("/courses-of-authenticated-user")
    public List<CourseResponse> getAllCoursesOfAuthenticatedUser(){
        return courseService.getCoursesOfAuthenticatedUser();
    }

    @GetMapping("/get-top-8-c")
    public List<Course> getTop8RatedCourses(){
        //it actually returns 8,typo no time to fix :\
        return courseService.getTopTenRatedCourses();
    }
}
