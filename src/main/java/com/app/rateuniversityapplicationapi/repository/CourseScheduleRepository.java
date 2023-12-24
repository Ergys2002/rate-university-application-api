package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, UUID> {
    CourseSchedule getCourseScheduleByCourseIdAndCourseDateAndCourseTimeAndDurationAndLectureHall(UUID courseId, LocalDate courseDate, LocalTime courseTime, double duration, String lectureHall);

}
