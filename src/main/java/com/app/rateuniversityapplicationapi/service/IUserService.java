package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.RegisterRequest;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    int getNumberOfStudents();

    User findUserById(UUID uuid);

}
