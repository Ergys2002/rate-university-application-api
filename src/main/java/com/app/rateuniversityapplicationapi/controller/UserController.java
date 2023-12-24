package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.*;
//import com.app.rateuniversityapplicationapi.entity.Review;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;

    @PostMapping(path = "/drop-course",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void dropCourse(@RequestBody EnrollRequest enrollRequest){
        userService.dropCourse(UUID.fromString(enrollRequest.getCourseId()),
                enrollRequest.getEmail());
    }

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

    @GetMapping("/user/logged-in-user")
    public UserResponse getLoggedInUser(){return userService.getCurrentUser();}

    @PutMapping("/user/update-profile")
    public void updateUserProfile(
            @RequestBody UpdateUserRequest request) {

        userService.updateUser(request);

    }
}
