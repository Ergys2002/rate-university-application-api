package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.LecturerDTO;
import com.app.rateuniversityapplicationapi.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    // Endpoint to get all lecturers
    @GetMapping
    public ResponseEntity<List<LecturerDTO>> getAllLecturers() {
        List<LecturerDTO> lecturers = lecturerService.getAllLecturers();
        return new ResponseEntity<>(lecturers, HttpStatus.OK);
    }

    // Endpoint to get a specific lecturer by ID
    @GetMapping("/{id}")
    public ResponseEntity<LecturerDTO> getLecturerById(@PathVariable UUID id) {
        LecturerDTO lecturer = lecturerService.getLecturerById(id);
        if (lecturer != null) {
            return new ResponseEntity<>(lecturer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
