package com.app.rateuniversityapplicationapi.service.interfaces;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;

import java.util.List;
import java.util.UUID;

public interface ILecturerService {

    List<LecturerResponse> getAllLecturers();

    LecturerResponse getLecturerById(UUID id);

    int getNumberOfLecturers();

    List<String> getLecturerIds();
}

