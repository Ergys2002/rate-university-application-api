package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.responses.CourseScheduleResponse;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import com.app.rateuniversityapplicationapi.service.classes.CourseScheduleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CourseScheduleController {

    private final CourseScheduleService  courseScheduleService;
    @GetMapping
    public ResponseEntity<List<CourseScheduleResponse>> getCourseSchedule(){
        return new ResponseEntity<>(courseScheduleService.getCourseSchedule(), HttpStatus.OK);
    }

}
