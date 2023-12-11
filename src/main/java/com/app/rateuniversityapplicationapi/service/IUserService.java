package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.dto.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);
}
