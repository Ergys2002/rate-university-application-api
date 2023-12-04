package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
