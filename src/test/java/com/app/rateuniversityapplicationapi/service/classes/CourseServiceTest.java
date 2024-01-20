package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.classes.CourseService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private IUserService userService;
    @InjectMocks
    private CourseService courseService;

    private UUID sampleCourseId;
    private String sampleEmail;
    private Course sampleCourse;
    private User sampleUser;
    private UserResponse authenticatedUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCourseId = UUID.fromString("ce496851-f3bd-4e60-8f42-f2ba99de137e");
        sampleEmail = "ergysxhaollari02@gmail.com";

        // Set up a sample course and user
        sampleCourse = new Course();
        sampleCourse.setId(sampleCourseId);
        sampleCourse.setTitle("Sample Course");
        // ... other setters for sampleCourse

        sampleUser = new User();
        sampleUser.setId(UUID.randomUUID());
        sampleUser.setEmail(sampleEmail);

        when(courseRepository.findById(sampleCourseId)).thenReturn(Optional.of(sampleCourse));
        when(userRepository.findByEmail(sampleEmail)).thenReturn(sampleUser);
    }

    @Test
    void isEnrolledTest() {
        Set<Course> enrolledCourses = new HashSet<>();
        enrolledCourses.add(sampleCourse);
        sampleUser.setEnrolledCourses(enrolledCourses);

        boolean isEnrolled = courseService.isEnrolled(sampleCourseId, sampleEmail);
        assertFalse(isEnrolled, "User should be enrolled in the course");
    }

    @Test
    void dropCourseTest() {
        // Arrange
        when(courseRepository.getCourseById(sampleCourseId)).thenReturn(sampleCourse);
        when(userRepository.findByEmail(sampleEmail)).thenReturn(sampleUser);
//        doNothing().when(courseRepository).save(any(Course.class));

        // Act
        boolean result = courseService.dropCourse(sampleCourseId, sampleEmail);

        // Assert
        assertTrue(result, "The course should be dropped successfully");
    }

    @Test
    void findAllByPageNumberTest() {
        // Arrange
        int pageNumber = 1;
        Page<Course> page = new PageImpl<>(List.of(sampleCourse));
        when(courseRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        List<Course> courses = courseService.findAllByPageNumber(pageNumber);

        // Assert
        assertFalse(courses.isEmpty(), "Courses list should not be empty");
        assertEquals(sampleCourse.getTitle(), courses.get(0).getTitle(), "Course title should match");
    }
//    @Test
//    void testAppendUser() {
//
//        when(courseRepository.findById(sampleCourseId)).thenReturn(Optional.of(sampleCourse));
//
//        // Setup
//        when(userRepository.findByEmail(sampleEmail)).thenReturn(sampleUser);
//        when(courseRepository.findById(sampleCourseId)).thenReturn(Optional.of(sampleCourse));
//
//        // Execution
//        courseService.appendUser(sampleEmail, sampleCourseId);
//
//        // Assertion
//        assertTrue(sampleCourse.getRegisteredStudents().contains(sampleUser), "User should be appended to the course");
//    }


    @Test
    void getAllCoursesTest() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(List.of(sampleCourse));

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertFalse(courses.isEmpty(), "Courses list should not be empty");
        assertEquals(sampleCourse.getTitle(), courses.get(0).getTitle(), "Course title should match");
    }


    @Test
    void getCoursesByNameTest() {
        // Arrange
        String courseName = "Sample";
        when(courseRepository.findCourseByTitleContainingIgnoreCase(courseName)).thenReturn(List.of(sampleCourse));

        // Act
        List<Course> courses = courseService.getCoursesByName(courseName);

        // Assert
        assertFalse(courses.isEmpty(), "Courses list should not be empty");
        assertEquals(sampleCourse.getTitle(), courses.get(0).getTitle(), "Course title should match");
    }


    @Test
    void testGetTopTenRatedCourses() {
        List<Course> topRatedCourses = List.of(sampleCourse);
        when(courseRepository.getTop10RatedCourses()).thenReturn(topRatedCourses);

        List<Course> result = courseService.getTopTenRatedCourses();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(topRatedCourses.size(), result.size());
        assertEquals(topRatedCourses.get(0), result.get(0));
        verify(courseRepository).getTop10RatedCourses();
    }

    @Test
    void testGetAllAvailableCourses() {
        List<Course> availableCourses = List.of(sampleCourse);
        when(courseRepository.getAllAvailableCourses(true)).thenReturn(availableCourses);

        List<Course> result = courseService.getAllAvailableCourses();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(availableCourses.size(), result.size());
        assertEquals(availableCourses.get(0), result.get(0));
        verify(courseRepository).getAllAvailableCourses(true);
    }


    @Test
    void testGetCourseById() {
        CourseResponse expectedResponse = new CourseResponse() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getTitle() {
                return "Sample Course";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public LocalDate getStartDate() {
                return null;
            }

            @Override
            public LocalDate getEndDate() {
                return null;
            }

            @Override
            public boolean isAvailable() {
                return false;
            }

            @Override
            public int getTotalQuotes() {
                return 0;
            }

            @Override
            public int getEnrolledStudents() {
                return 0;
            }

            @Override
            public double getRating() {
                return 0;
            }

            @Override
            public String getLecturerId() {
                return null;
            }

            @Override
            public String getPicture() {
                return null;
            }
        };

        when(courseRepository.getCourseById(sampleCourseId)).thenReturn(sampleCourse);

        CourseResponse result = courseService.getCourseById(sampleCourseId);

        assertNotNull(result);
        assertEquals(expectedResponse.getTitle(), result.getTitle());
        assertEquals(expectedResponse.getDescription(), result.getDescription());
        verify(courseRepository).getCourseById(sampleCourseId);
    }

    @Test
    void testGetAllCoursesOfALecturer() {
        List<Course> lecturerCourses = List.of(sampleCourse);
        UUID lecturerId = UUID.randomUUID();
        when(courseRepository.findCoursesByLecturerId(lecturerId)).thenReturn(lecturerCourses);

        List<CourseResponse> result = courseService.getAllCoursesOfALecturer(lecturerId.toString());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(lecturerCourses.size(), result.size());
        assertEquals(lecturerCourses.get(0).getTitle(), result.get(0).getTitle());
        verify(courseRepository).findCoursesByLecturerId(lecturerId);
    }

    @Test
    void testGetNumberOfCourses() {
        int expectedCount = 10;
        when(courseRepository.getNumberOfCourses()).thenReturn(expectedCount);

        int result = courseService.getNumberOfCourses();

        assertEquals(expectedCount, result);
        verify(courseRepository).getNumberOfCourses();
    }

    @Test
    void testGetCoursesOfAuthenticatedUser() {
        UserResponse mockUserResponse = new UserResponse() {
            @Override
            public String getFirstName() {
                return null;
            }

            @Override
            public String getLastName() {
                return null;
            }

            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public LocalDate getBirthDate() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public String getProfilePhotoUrl() {
                return null;
            }
        }; // Use actual constructor or builder pattern
        // Set necessary properties of mockUserResponse here, such as email
        List<Course> userCourses = List.of(sampleCourse);

        // Stub userService.getCurrentUser() to return mockUserResponse
        when(userService.getCurrentUser()).thenReturn(mockUserResponse);
        when(courseRepository.findAllCoursesByCurrentUser(mockUserResponse.getEmail())).thenReturn(userCourses);

        List<CourseResponse> result = courseService.getCoursesOfAuthenticatedUser();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userCourses.size(), result.size());
        assertEquals(userCourses.get(0).getTitle(), result.get(0).getTitle());
        verify(courseRepository).findAllCoursesByCurrentUser(mockUserResponse.getEmail());
    }


}
