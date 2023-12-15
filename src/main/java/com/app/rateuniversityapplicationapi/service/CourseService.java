package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public CourseResponse getCourseById(UUID courseId) {
        Course courseFromDb = courseRepository.getCourseById(courseId);

        return new CourseResponse() {
            @Override
            public String getId() {
                return courseFromDb.getId().toString();
            }

            @Override
            public String getTitle() {
                return courseFromDb.getTitle();
            }

            @Override
            public String getDescription() {
                return courseFromDb.getDescription();
            }

            @Override
            public LocalDate getStartDate() {
                return courseFromDb.getStartDate();
            }

            @Override
            public LocalDate getEndDate() {
                return courseFromDb.getEndDate();
            }

            @Override
            public boolean isAvailable() {
                return courseFromDb.isAvailable();
            }

            @Override
            public int getTotalQuotes() {
                return courseFromDb.getTotalQuotes();
            }

            @Override
            public int getFreeQuotes() {
                return courseFromDb.getFreeQuotes();
            }

            @Override
            public double getRating() {
                return courseFromDb.getCourseRating();
            }

            @Override
            public String getLecturerId() {
                return courseFromDb.getLecturerId().toString();
            }
        };
    }

    @Override
    public int getNumberOfCourses() {
        return courseRepository.getNumberOfCourses();
    }

}
