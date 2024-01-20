package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.responses.CourseScheduleResponse;
import com.app.rateuniversityapplicationapi.service.classes.CourseScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseScheduleControllerTest {

    @Mock
    private CourseScheduleService courseScheduleService;

    @InjectMocks
    private CourseScheduleController courseScheduleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseScheduleController).build();
    }

    @Test
    void getCourseScheduleTest() throws Exception {
        List<CourseScheduleResponse> courseScheduleResponses = new ArrayList<>(); // Mock data
        when(courseScheduleService.getCourseSchedule()).thenReturn(courseScheduleResponses);

        mockMvc.perform(get("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // .andExpect(content().json(/* JSON representation of courseScheduleResponses */)); // Optional

        verify(courseScheduleService, times(1)).getCourseSchedule();
    }
}
