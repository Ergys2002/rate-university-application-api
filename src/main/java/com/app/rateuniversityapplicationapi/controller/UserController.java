package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.requests.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.requests.EnrollRequest;
import com.app.rateuniversityapplicationapi.dto.requests.UpdateUserRequest;
import com.app.rateuniversityapplicationapi.dto.responses.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.requests.RegisterRequest;
import com.app.rateuniversityapplicationapi.service.interfaces.IUserService;

import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return userService.authenticate(request);
    }

    @GetMapping("/students/number-of-students")
    public int getNumberOfStudents(){
        return userService.getNumberOfStudents();
    }

    @GetMapping("/user/logged-in-user")
    public UserResponse getLoggedInUser(){return userService.getCurrentUser();}

    @PutMapping(value = "/user/update-profile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public ResponseEntity<AuthenticationResponse> updateUserProfile(
            @ModelAttribute UpdateUserRequest updateUserRequest
    ) {
        System.out.println(updateUserRequest);
        return userService.updateUser(updateUserRequest);
    }
}
