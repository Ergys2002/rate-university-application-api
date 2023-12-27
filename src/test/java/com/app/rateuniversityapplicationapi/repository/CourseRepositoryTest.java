package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {

        Course course = Course.builder()
                .title("Advanced Calculus")
                .description("Master the intricacies of advanced mathematical concepts and applications.")
                .totalQuotes(12)
                .enrolledStudents(1)
                .courseRating(5.0)
                .isAvailable(true)
                .build();

        testEntityManager.persist(course);
    }

    @Test
    public void whenGetByCourseName_thenReturnCourse() {
        List<Course> course =
                courseRepository.getCourseByCourseName("Advanced Calculus");

        assertEquals(course.get(0).getTitle(),"Advanced Calculus");
    }

    @Test
    public void whenFindCourseByTitleContainingIgnoreCase_thenReturnCourses() {
        List<Course> course =
                courseRepository.findCourseByTitleContainingIgnoreCase("Advanced Calculus");

        assertEquals(course.get(0).getTitle(),"Advanced Calculus");
    }

    @Test
    public void whenGetAllAvailableCourses_thenReturnListOfAvailableCourses(){
        List<Course> availableCourses =
                courseRepository.getAllAvailableCourses(true);
        assertEquals(availableCourses.size(),1);
    }

    @Test
    public void whenGetTop10RatedCourses_thenSortCourses(){
        Course course1 = Course.builder()
                .title("Maths")
                .description("Master the intricacies of advanced mathematical concepts and applications.")
                .totalQuotes(12)
                .enrolledStudents(1)
                .courseRating(12)
                .isAvailable(true)
                .build();

        testEntityManager.persist(course1);

        List<Course> top10RatedCourses =
                courseRepository.getTop10RatedCourses();

        assertEquals(top10RatedCourses.get(0),course1);
    }

    @Test
    public void whenFindCoursesByLecturerId_thenReturnCourse(){
        Lecturer lecturer = Lecturer.builder().build();

        Course course1 = Course.builder()
                .title("Maths")
                .description("Master the intricacies of advanced mathematical concepts and applications.")
                .totalQuotes(12)
                .enrolledStudents(1)
                .courseRating(12)
                .isAvailable(true)
                .lecturerId(lecturer.getId())
                .build();

        testEntityManager.persist(course1);

        List<Course> courses =
                courseRepository.findCoursesByLecturerId(lecturer.getId());

        assertEquals(courses.get(1).getTitle(),course1.getTitle());
    };
}