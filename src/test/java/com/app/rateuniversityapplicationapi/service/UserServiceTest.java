//package com.app.rateuniversityapplicationapi.service;
//
//import com.app.rateuniversityapplicationapi.dto.requests.RegisterRequest;
//import com.app.rateuniversityapplicationapi.entity.Role;
//import com.app.rateuniversityapplicationapi.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void registerUser(){
//        RegisterRequest request = RegisterRequest.builder()
//                .firstname("admin")
//                .lastname("admin")
//                .birthDate(LocalDate.now())
//                .email("maksimoramaj@gmail.com")
//                .password("1234")
//                .phoneNumber("0676653349")
//                .profilePhotoURL("")
//                .build();
//        userService.register(request);
//    }
//}