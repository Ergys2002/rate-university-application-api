package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.LecturerResponse;

import java.util.List;
import java.util.UUID;

public interface ILecturerService {

    List<LecturerResponse> getAllLecturers();

    LecturerResponse getLecturerById(UUID id);

    int getNumberOfLecturers();

    List<String> getLecturerIds();
}

