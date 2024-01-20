package com.app.rateuniversityapplicationapi.service.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.CourseScheduleRepository;
import com.app.rateuniversityapplicationapi.dto.responses.CourseScheduleResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CourseScheduleServiceTest {

    @Mock
    private CourseScheduleRepository courseScheduleRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseScheduleService courseScheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCourseSchedule() {
        List<CourseSchedule> mockCourseSchedules = IntStream.range(0, 5)
                .mapToObj(i -> {
                    CourseSchedule cs = new CourseSchedule();
                    cs.setId(UUID.randomUUID());
                    cs.setLectureHall("Hall " + i);
                    cs.setCourseDate(LocalDate.now());
                    cs.setCourseTime(LocalTime.now());
                    cs.setDuration(2.0);
                    cs.setCourseId(UUID.randomUUID()); // Sample course ID
                    return cs;
                }).collect(Collectors.toList());

        List<Course> mockCourses = mockCourseSchedules.stream()
                .map(cs -> {
                    Course c = new Course();
                    c.setId(cs.getCourseId());
                    c.setTitle("Course " + cs.getId());
                    return c;
                }).collect(Collectors.toList());

        when(courseScheduleRepository.findAll()).thenReturn(mockCourseSchedules);
        mockCourseSchedules.forEach(cs ->
                when(courseRepository.getCourseById(cs.getCourseId())).thenReturn(mockCourses.stream()
                        .filter(c -> c.getId().equals(cs.getCourseId()))
                        .findFirst()
                        .orElse(null))
        );

        List<CourseScheduleResponse> result = courseScheduleService.getCourseSchedule();

        assertNotNull(result);
        assertEquals(mockCourseSchedules.size(), result.size());
        for (int i = 0; i < mockCourseSchedules.size(); i++) {
            CourseScheduleResponse response = result.get(i);
            CourseSchedule cs = mockCourseSchedules.get(i);
            assertEquals(cs.getId().toString(), response.getId());
            assertEquals(cs.getLectureHall(), response.getLectureHall());
            assertEquals(cs.getCourseDate().toString(), response.getCourseDate());
            assertEquals(cs.getCourseTime().toString(), response.getCourseTime());
            assertEquals(cs.getDuration(), response.getDuration());
            // Asserting the course title
            Course course = mockCourses.stream()
                    .filter(c -> c.getId().equals(cs.getCourseId()))
                    .findFirst()
                    .orElse(null);
            assertEquals(course.getTitle(), response.getCourseTitle());
        }

        verify(courseScheduleRepository).findAll();
        mockCourseSchedules.forEach(cs ->
                verify(courseRepository).getCourseById(cs.getCourseId())
        );
    }
}
