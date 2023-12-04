package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.LecturerDTO;

import java.util.List;
import java.util.UUID;

public interface ILecturerService {

    List<LecturerDTO> getAllLecturers();

    LecturerDTO getLecturerById(UUID id);

}

