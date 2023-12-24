package com.app.rateuniversityapplicationapi.service.interfaces;

import com.app.rateuniversityapplicationapi.dto.responses.CourseScheduleResponse;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;

import java.util.List;

public interface ICourseScheduleService {
    List<CourseScheduleResponse> getCourseSchedule();
}
