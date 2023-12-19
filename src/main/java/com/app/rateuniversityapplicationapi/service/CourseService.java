package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.StudentResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.exceptions.UserNotFoundException;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService{

    private final CourseRepository courseRepository;
    //Appended just for appendUser method (if error occurs delete)
    private final UserRepository userRepository;

    @Override
    public boolean isEnrolled(UUID courseId, String email) {
        List<StudentResponse> enrolledStudents = this.getUsersByEnrolledCourseContains(courseId);

        return enrolledStudents.stream()
                .anyMatch(enrolledStudent->enrolledStudent.getEmail().equals(email));
    }


    @Override
    public void appendUser(String userEmail,UUID courseId) {
        Course course = courseRepository.getCourseById(courseId);
        User user = userRepository.findByEmail(userEmail);

        Set<User> users = course.getRegisteredStudents();

        users.add(user);

        course.setRegisteredStudents(users);
        course.setEnrolledStudents(users.size());
        courseRepository.save(course);
    }

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
                return courseFromDb.getEnrolledStudents();
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

    @Override
    public List<StudentResponse> getUsersByEnrolledCourseContains(UUID courseUUID) {
        Course course = courseRepository.getCourseById(courseUUID);
        List<User> users = userRepository.getUsersByEnrolledCoursesContains(course);
        List<StudentResponse> students = users.stream().map(user -> new StudentResponse() {
            @Override
            public String getFirstName() {
                return user.getFirstname();
            }

            @Override
            public String getLastName() {
                return user.getLastname();
            }

            @Override
            public String getEmail() {
                return user.getEmail();
            }
        }).collect(Collectors.toList());

        return students;
    }

}
