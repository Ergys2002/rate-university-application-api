package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.dto.LecturerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.rateuniversityapplicationapi.entity.Lecturer;

import java.util.List;
import java.util.UUID;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {

    @Query("SELECT u FROM Lecturer u")
    List<LecturerDTO> getAll();

    LecturerDTO getLecturerById(@Param("id") UUID id);
}
