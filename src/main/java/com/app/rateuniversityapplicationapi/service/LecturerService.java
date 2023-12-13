package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.LecturerResponse;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LecturerService implements ILecturerService {


    private final LecturerRepository lecturerRepository;

    @Override
    public List<LecturerResponse> getAllLecturers() {

        return lecturerRepository.getAll();
    }

    @Override
    public LecturerResponse getLecturerById(UUID id) {
          return lecturerRepository.getLecturerById(id);
    }

    @Override
    public int getNumberOfLecturers() {
        return lecturerRepository.getNumberOfLecturers();
    }
}
