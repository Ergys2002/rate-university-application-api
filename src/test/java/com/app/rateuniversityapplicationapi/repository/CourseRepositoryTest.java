package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void saveCourseWithUser(){
        User user = User.builder()
                .firstname("TestUser")
                .lastname("Tst1")
        .build();

        Set<User> users = new HashSet<>();
        users.add(user);
        Course course = Course.builder()
                .registeredStudents(users)
                .title("TGJP")
                .description("Test")
                .build();

        courseRepository.save(course);
    }
}