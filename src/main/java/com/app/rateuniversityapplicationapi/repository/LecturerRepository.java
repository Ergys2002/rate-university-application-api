package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.dto.LecturerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.rateuniversityapplicationapi.entity.Lecturer;

import java.util.List;
import java.util.UUID;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {

    @Query("SELECT u FROM Lecturer u")
    List<LecturerResponse> getAll();

    Lecturer getLecturerById(UUID id);

    @Query("SELECT COUNT(l.id) FROM Lecturer l")
    int getNumberOfLecturers();

    Lecturer getLecturersByEmail(String email);

}
