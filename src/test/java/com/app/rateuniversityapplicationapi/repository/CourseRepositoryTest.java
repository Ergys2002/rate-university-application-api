package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LecturerRepository lecturerRepository;

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
                .email("RandomLektor")
                .firstName("RandomFirstName")
                .lastName("RandomLastName")
                .rating(92)
                .build();
        lecturerRepository.save(lecturer);
        Course course = Course.builder()
                .title("RandomTitle")
                .description("In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying")
                .totalQuotes(85)
                .freeQuotes(22)
                .courseRating(27.3)
                .lecturer(lecturer)
                .lecturerId(lecturer.getId())
                .endDate(LocalDate.now())
                .startDate(LocalDate.of(2024,12,14))
                .isAvailable(true)
                .build();
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
                .freeQuotes(10)
                .build();

        courseRepository.save(course);
        //when
        List<Course> retrievedCourses = courseRepository.getCourseByCourseName("Fizik1");

        //then
        Course retrievedCourse = retrievedCourses.get(0);
        assertThat(retrievedCourse.getTitle()).isEqualTo("Fizik1");
        assertThat(retrievedCourse.getIsAvailable()).isTrue();
        assertThat(retrievedCourse.getFreeQuotes()).isEqualTo(10);
    }

    @Test
    public void whenGetAllAvailableCourses_thenSuccess() {
        // Given
        Course availableCourse = Course.builder()
                .title("Physics101")
                .isAvailable(true)
                .freeQuotes(20)
                .build();

        Course notAvailableCourse = Course.builder()
                .title("Chemistry101")
                .isAvailable(false)
                .freeQuotes(15)
                .build();

        courseRepository.save(availableCourse);
        courseRepository.save(notAvailableCourse);

        // When
        List<Course> availableCourses = courseRepository.getAllAvailableCourses(true);

        // Then
        Course retrievedCourse = availableCourses.get(availableCourses.size()-1);
        assertThat(retrievedCourse.getTitle()).isEqualTo("Physics101");
        assertThat(retrievedCourse.getIsAvailable()).isTrue();
        assertThat(retrievedCourse.getFreeQuotes()).isEqualTo(20);
    }

}