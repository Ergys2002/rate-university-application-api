package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.LecturerDTO;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerService implements ILecturerService {



    private final LecturerRepository lecturerRepository;

    @Override
    public List<LecturerDTO> getAllLecturers() {

        return lecturerRepository.getAll();
    }

    @Override
    public LecturerDTO getLecturerById(UUID id) {
          return lecturerRepository.getLecturerById(id);
    }
}
