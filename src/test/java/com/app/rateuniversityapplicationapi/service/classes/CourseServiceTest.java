package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Role;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import jdk.dynalink.linker.LinkerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {

        Course course = Course.builder()
                .title("SpringBoot test case!")
                .description("This is a test!")
                .totalQuotes(100)
                .courseRating(75)
                .build();

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        Mockito.when(courseRepository
                .findCourseByTitleContainingIgnoreCase("SpringBoot test case!"))
                .thenReturn(courseList);
    }

    @Test
    public void whenValidCourseTitle_thenCoursesShouldBeFound(){
            List<Course> foundCourse = courseService
                    .getCoursesByName("SpringBoot test case!");

            assertEquals(foundCourse.get(0).getTitle(),"SpringBoot test case!");
    }



}