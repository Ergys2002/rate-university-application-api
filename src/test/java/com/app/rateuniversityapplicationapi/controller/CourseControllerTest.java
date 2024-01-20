package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.requests.CourseRequest;
import com.app.rateuniversityapplicationapi.dto.requests.EnrollRequest;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.dto.responses.CourseResponse;
import com.app.rateuniversityapplicationapi.dto.responses.StudentResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.ICourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    @Mock
    private ICourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void getAllCoursesTest() throws Exception {
        List<Course> courses = new ArrayList<>(); // create mock Course list
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courses */)); // Add JSON verification if necessary
    }

    @Test
    void isEnrolledTest() throws Exception {
        EnrollRequest enrollRequest = new EnrollRequest("user@example.com", "0838738f-f779-435f-8c01-619ad1ca40d9");
        when(courseService.isEnrolled(UUID.fromString(enrollRequest.getCourseId()), enrollRequest.getEmail())).thenReturn(true);

        mockMvc.perform(post("/api/courses/isEnrolled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(enrollRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void getCoursesByIdTest() throws Exception {
        UUID courseId = UUID.fromString("0838738f-f779-435f-8c01-619ad1ca40d9");

        // Create an instance of CourseResponse (replace with actual implementation)
        CourseResponse courseResponse = new CourseResponse() {
            // Implement all methods of CourseResponse interface
            @Override
            public String getId() {
                return "0838738f-f779-435f-8c01-619ad1ca40d9";
            }

            @Override
            public String getTitle() {
                return "Entrepreneurship Fundamentals";
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
            // ... other methods ...
        };

        when(courseService.getCourseById(courseId)).thenReturn(courseResponse);

        mockMvc.perform(get("/api/courses/details?id=" + courseId.toString()))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courseResponse */)); // Add JSON verification if necessary
    }

    @Test
    void getCourseByPageNumberTest() throws Exception {
        int pageNumber = 1;
        List<Course> courses = new ArrayList<>(); // Populate with sample Course objects
        when(courseService.findAllByPageNumber(pageNumber)).thenReturn(courses);

        mockMvc.perform(get("/api/courses/page/" + pageNumber))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courses */)); // Optional
    }

    @Test
    void addStudentTest() throws Exception {
        EnrollRequest enrollRequest = new EnrollRequest("user@example.com", "0838738f-f779-435f-8c01-619ad1ca40d9");

        // No return type, so no need to mock a return value
        mockMvc.perform(post("/api/courses/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(enrollRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCoursesOfALecturerTest() throws Exception {
        String lecturerId = "lecturer123";
        List<CourseResponse> courseResponses = new ArrayList<>(); // Populate with sample CourseResponse objects
        when(courseService.getAllCoursesOfALecturer(lecturerId)).thenReturn(courseResponses);

        mockMvc.perform(get("/api/courses/all-courses-of-a-lecturer")
                        .param("id", lecturerId))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courseResponses */)); // Optional
    }

    @Test
    void getTopRatedCoursesTest() throws Exception {
        List<Course> courses = new ArrayList<>(); // Populate with sample Course objects
        when(courseService.getTopTenRatedCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses/top-rated-courses"))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courses */)); // Optional
    }

    @Test
    void getCoursesOfAuthenticatedUserTest() throws Exception {
        List<CourseResponse> courseResponses = new ArrayList<>(); // Mock data
        when(courseService.getCoursesOfAuthenticatedUser()).thenReturn(courseResponses);

        mockMvc.perform(get("/api/courses/courses-of-authenticated-user"))
                .andExpect(status().isOk());
    }

    @Test
    void getCoursesByNameTest() throws Exception {
        CourseRequest courseRequest = new CourseRequest("Sample Course");
        List<Course> courses = new ArrayList<>(); // Mock data
        when(courseService.getCoursesByName(courseRequest.getCourseName())).thenReturn(courses);

        mockMvc.perform(post("/api/courses/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courseRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllAvailableCoursesTest() throws Exception {
        List<Course> availableCourses = new ArrayList<>(); // Mock some data
        when(courseService.getAllAvailableCourses()).thenReturn(availableCourses);

        mockMvc.perform(get("/api/courses/available-courses"))
                .andExpect(status().isOk());
        // Optionally add .andExpect(content().json(...)) to validate the response
    }

    @Test
    void getNumberOfCoursesTest() throws Exception {
        int numberOfCourses = 5; // Example value
        when(courseService.getNumberOfCourses()).thenReturn(numberOfCourses);

        mockMvc.perform(get("/api/courses/number-of-courses"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(numberOfCourses)));

        verify(courseService, times(1)).getNumberOfCourses();
    }

    @Test
    void getAllUsersByEnrolledCourseTest() throws Exception {
        String courseUUID = "0838738f-f779-435f-8c01-619ad1ca40d9";
        List<StudentResponse> enrolledStudents = new ArrayList<>();
        when(courseService.getUsersByEnrolledCourseContains(UUID.fromString(courseUUID)))
                .thenReturn(enrolledStudents);

        mockMvc.perform(get("/api/courses/enrolled-students/" + courseUUID))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of enrolledStudents */)); // Optional

        verify(courseService, times(1)).getUsersByEnrolledCourseContains(UUID.fromString(courseUUID));
    }

    @Test
    void getTop8RatedCoursesTest() throws Exception {
        List<Course> topRatedCourses = new ArrayList<>(); // Mock data
        when(courseService.getTopTenRatedCourses()).thenReturn(topRatedCourses);

        mockMvc.perform(get("/api/courses/get-top-rated-courses"))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of topRatedCourses */)); // Optional

        verify(courseService, times(1)).getTopTenRatedCourses();
    }


}
