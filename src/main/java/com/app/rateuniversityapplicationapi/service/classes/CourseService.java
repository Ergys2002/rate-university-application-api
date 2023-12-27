package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.responses.StudentResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.interfaces.ICourseService;
import com.app.rateuniversityapplicationapi.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    //Appended just for appendUser method (if error occurs delete)
    private final UserRepository userRepository;

    private final IUserService userService;

    @Override
    public boolean isEnrolled(UUID courseId, String email) {
        List<StudentResponse> enrolledStudents = this.getUsersByEnrolledCourseContains(courseId);

        return enrolledStudents.stream()
                .anyMatch(enrolledStudent->enrolledStudent.getEmail().equals(email));
    }


    @Override
    public boolean dropCourse(UUID courseId,String userEmail) {
        Course course = courseRepository.getCourseById(courseId);
        User user = userRepository.findByEmail(userEmail);

        Set<Course> enrolledCourses = user.getEnrolledCourses();
        System.out.println(enrolledCourses);

        courseRepository.save(course);
        return true;
    }

    @Transactional
    @Override
    public void appendUser(String userEmail,UUID courseId) {
        Course course = courseRepository.getCourseById(courseId);
        User user = userRepository.findByEmail(userEmail);

        Set<User> users = course.getRegisteredStudents();
        System.out.println("USERSSS " + users);
        users.add(user);

        System.out.println("Users" + user);
        System.out.println("Users dot size " +users.size());

        course.setRegisteredStudents(users);
        course.setEnrolledStudents(userRepository.getUsersByEnrolledCoursesContains(course).size());

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
        List<Course> courses = courseRepository.findCourseByTitleContainingIgnoreCase(name);
        System.out.println(courses);
        return courses;
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
            public int getEnrolledStudents() {
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

            @Override
            public String getPicture() {
                return courseFromDb.getPicture();
            }
        };
    }

    public List<CourseResponse> getAllCoursesOfALecturer(String id){
        List<Course> coursesFromDb = courseRepository.findCoursesByLecturerId(UUID.fromString(id));
       return coursesFromDb.stream().map(course -> new CourseResponse() {
            @Override
            public String getId() {
                return course.getId().toString();
            }

            @Override
            public String getTitle() {
                return course.getTitle();
            }

            @Override
            public String getDescription() {
                return course.getDescription();
            }

            @Override
            public LocalDate getStartDate() {
                return course.getStartDate();
            }

            @Override
            public LocalDate getEndDate() {
                return course.getEndDate();
            }

            @Override
            public boolean isAvailable() {
                return course.isAvailable();
            }

            @Override
            public int getTotalQuotes() {
                return course.getTotalQuotes();
            }

            @Override
            public int getEnrolledStudents() {
                return course.getEnrolledStudents();
            }

            @Override
            public double getRating() {
                return course.getCourseRating();
            }

           @Override
           public String getLecturerId() {
               return course.getLecturerId().toString();
           }

           @Override
           public String getPicture() {
               return course.getPicture();
           }
       }).collect(Collectors.toList());
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

    public List<CourseResponse> getCoursesOfAuthenticatedUser(){
        UserResponse authenticatedUser = userService.getCurrentUser();

        List<Course> coursesOfCurrentUserFromDb = courseRepository.findAllCoursesByCurrentUser(authenticatedUser.getEmail());

        return coursesOfCurrentUserFromDb.stream().map(course -> new CourseResponse() {
            @Override
            public String getId() {
                return course.getId().toString();
            }

            @Override
            public String getTitle() {
                return course.getTitle();
            }

            @Override
            public String getDescription() {
                return course.getDescription();
            }

            @Override
            public LocalDate getStartDate() {
                return course.getStartDate();
            }

            @Override
            public LocalDate getEndDate() {
                return course.getEndDate();
            }

            @Override
            public boolean isAvailable() {
                return course.isAvailable();
            }

            @Override
            public int getTotalQuotes() {
                return course.getTotalQuotes();
            }

            @Override
            public int getEnrolledStudents() {
                return course.getEnrolledStudents();
            }

            @Override
            public double getRating() {
                return course.getCourseRating();
            }

            @Override
            public String getLecturerId() {
                return course.getLecturerId().toString();
            }

            @Override
            public String getPicture() {
                return course.getPicture();
            }
        }).collect(Collectors.toList());
    }

}
