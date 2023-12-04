package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query(
            "select u from Course u where u.title like %?1"
    )
    public List<Course> getCourseByCourseName(String courseName);

    @Query(value = "SELECT * FROM rate_university_db WHERE is_Available = ?1",
                    nativeQuery = true
    )
    public List<Course> getAllAvailableCourses(Boolean isAvailable);


}
