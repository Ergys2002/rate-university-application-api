package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Course getCourseById(UUID courseId);

    //comment
    @Query(
            "select u from Course u where u.title like %?1"
    )
    List<Course> getCourseByCourseName(String courseName);

    @Query(
            value = "select u from Course u where u.isAvailable = ?1"
    )
    List<Course> getAllAvailableCourses(Boolean isAvailable);

    @Query("from Course order by courseRating desc limit 8")
    List<Course> getTop10RatedCourses();

    @Query("SELECT COUNT(c.id) FROM Course c")
    int getNumberOfCourses();
    @Query("select u from Course u ")
    List<Course> getAll();
    Course getCourseByTitleEqualsIgnoreCaseAndDescriptionEqualsIgnoreCaseAndLecturerId(String title, String description, UUID lecturerId);

    List<Course> findCoursesByLecturerId(UUID lecturerId);

    @Query("select u.enrolledCourses from User u where u.email = :email")
    List<Course> findAllCoursesByCurrentUser(@Param("email") String email);
}
