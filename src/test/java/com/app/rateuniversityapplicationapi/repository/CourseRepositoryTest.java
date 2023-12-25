package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.entity.Role;
import com.app.rateuniversityapplicationapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    UserRepository userRepository;

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

    @Test
    public void saveCourse(){
        Lecturer lecturer = Lecturer.builder()
                .id(UUID.randomUUID())
                .email("Jim_Joe@Gmail.com")
                .firstName("Jim")
                .lastName("Joe")
                .rating(95)
                .build();
        lecturerRepository.save(lecturer);

        User user = User.builder()
                .firstname("Admin")
                .lastname("Doe")
                .email("test@test@gm.al")
                .role(Role.USER)
                .phoneNumber("062 255 45 3")
                .build();
        userRepository.save(user);

        Set<User> users = new HashSet<>();
        users.add(user);

        Course course = Course.builder()
                .title("Angular TutorialsTEST")
                .description("Angular lets you start small and supports you as your team and apps grow. Read how Angular helps you grow. Loved by ...")
                .totalQuotes(50)
                .courseRating(99)
                .lecturer(lecturer)
                .lecturerId(lecturer.getId())
                .registeredStudents(users)
                .endDate(LocalDate.of(2023,12,14))
                .startDate(LocalDate.of(2022,12,14))
                .isAvailable(true)
                .build();
        course.setRegisteredStudents(users);
        courseRepository.save(course);
    }

    @Test
    public void findAllByPageNumber(int page) {
        Pageable pageRequest = PageRequest.of(1,6);

        System.out.println( courseRepository.findAll(pageRequest)
                .getContent() );
    }

    @Test
    public void whenGetCourseByCourseName_thenSuccess(){
        Course course = Course.builder()
                .title("Fizik1")
                .isAvailable(true)
                .build();

        courseRepository.save(course);
        //when
        List<Course> retrievedCourses = courseRepository.findCourseByTitleContainingIgnoreCase("Fizik1");

        //then
        Course retrievedCourse = retrievedCourses.get(0);
        assertThat(retrievedCourse.getTitle()).isEqualTo("Fizik1");
//        assertThat(retrievedCourse.getIsAvailable()).isTrue();
//        assertThat(retrievedCourse.getFreeQuotes()).isEqualTo(10);
    }

    @Test
    public void whenGetAllAvailableCourses_thenSuccess() {
        // Given
        Course availableCourse = Course.builder()
                .title("Physics101")
                .isAvailable(true)
//                .freeQuotes(20)
                .build();

        Course notAvailableCourse = Course.builder()
                .title("Chemistry101")
                .isAvailable(false)
//                .freeQuotes(15)
                .build();

        courseRepository.save(availableCourse);
        courseRepository.save(notAvailableCourse);

        // When
        List<Course> availableCourses = courseRepository.getAllAvailableCourses(true);

        // Then
        Course retrievedCourse = availableCourses.get(availableCourses.size()-1);
        assertThat(retrievedCourse.getTitle()).isEqualTo("Physics101");
//        assertThat(retrievedCourse.getIsAvailable()).isTrue();
//        assertThat(retrievedCourse.getFreeQuotes()).isEqualTo(20);
    }

//    @Test
//    public boolean Enrolled(){
//        String email = "masimo_ramaj@shqiptar.eu";
//        String courseId = "7b3be9b2-ffd0-4e69-959d-3a82eafa8268";
//
//        AtomicBoolean isEnrolled = new AtomicBoolean(false);
//
//        Set<Course> enrolledCourses = userRepository.findByEmail(email).getEnrolledCourses();
//
//        enrolledCourses.forEach(enrolledCourse -> {
//            if (enrolledCourse.getId() == UUID.fromString(courseId)) isEnrolled.set(true);
//        });
//
//        return isEnrolled.get();
//    }

}