package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import com.app.rateuniversityapplicationapi.service.interfaces.ILecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Lecturer lecturerFromDB = lecturerRepository.getLecturerById(id);

        return new LecturerResponse() {
            @Override
            public String getId() {
                return lecturerFromDB.getId().toString();
            }

            @Override
            public String getFirstName() {
                return lecturerFromDB.getFirstName();
            }

            @Override
            public String getLastName() {
                return lecturerFromDB.getLastName();
            }

            @Override
            public String getEmail() {
                return lecturerFromDB.getEmail();
            }

            @Override
            public double getRating() {
                return lecturerFromDB.getRating();
            }

            @Override
            public String getDescription() {
                return lecturerFromDB.getDescription();
            }

            @Override
            public String getProfilePicture() {
                return lecturerFromDB.getProfilePicture();
            }
        };
    }

    @Override
    public int getNumberOfLecturers() {
        return lecturerRepository.getNumberOfLecturers();
    }

    public List<String> getLecturerIds(){
        return lecturerRepository.getAll().stream().map(LecturerResponse::getId).collect(Collectors.toList());
    }
}
