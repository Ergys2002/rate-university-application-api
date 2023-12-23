package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.CourseScheduleResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.CourseScheduleRepository;
import com.app.rateuniversityapplicationapi.service.interfaces.ICourseScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseScheduleService implements ICourseScheduleService {

    private final CourseScheduleRepository courseScheduleRepository;
    private final CourseRepository courseRepository;
    @Override
    public List<CourseScheduleResponse> getCourseSchedule() {
        List<CourseSchedule> coursesFromDb = courseScheduleRepository.findAll();


        return coursesFromDb.stream().map(courseSchedule -> new CourseScheduleResponse() {

            Course course = courseRepository.getCourseById(courseSchedule.getCourseId());
            @Override
            public String getId() {
                return courseSchedule.getId().toString();
            }

            @Override
            public String getLectureHall() {
                return courseSchedule.getLectureHall();
            }

            @Override
            public String getCourseDate() {
                return courseSchedule.getCourseDate().toString();
            }

            @Override
            public String getCourseTime() {
                return courseSchedule.getCourseTime().toString();
            }

            @Override
            public double getDuration() {
                return courseSchedule.getDuration();
            }

            @Override
            public String getCourseTitle() {
                return course.getTitle();
            }
        }).collect(Collectors.toList());
    }
}
