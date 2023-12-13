package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.RegisterRequest;
import com.app.rateuniversityapplicationapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/user/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("/students/number-of-students")
    public int getNumberOfStudents(){
        return userService.getNumberOfStudents();
    }
}
