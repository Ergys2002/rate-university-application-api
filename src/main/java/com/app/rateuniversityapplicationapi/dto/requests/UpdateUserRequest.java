package com.app.rateuniversityapplicationapi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private MultipartFile profilePhoto;
}
