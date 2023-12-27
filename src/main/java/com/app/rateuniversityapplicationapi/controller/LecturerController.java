package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.ILecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final ILecturerService lecturerService;

    @GetMapping
    public ResponseEntity<List<LecturerResponse>> getAllLecturers() {
        List<LecturerResponse> lecturers = lecturerService.getAllLecturers();
        return new ResponseEntity<>(lecturers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponse> getLecturerById(@PathVariable UUID id) {
        LecturerResponse lecturer = lecturerService.getLecturerById(id);
        if (lecturer != null) {
            return new ResponseEntity<>(lecturer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/number-of-lecturers")
    public int getNumberOfLecturers(){
        return lecturerService.getNumberOfLecturers();
    }

    @GetMapping("/lecturer-ids")
    public List<String> getLecturerIds(){
        return lecturerService.getLecturerIds();
    }
}
