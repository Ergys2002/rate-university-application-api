package com.app.rateuniversityapplicationapi.service.interfaces;

import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.responses.StudentResponse;
import com.app.rateuniversityapplicationapi.entity.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    boolean isEnrolled(UUID courseId, String email);

    boolean dropCourse(UUID courseId,String userEmail);

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


    List<CourseResponse> getCoursesOfAuthenticatedUser();
}
