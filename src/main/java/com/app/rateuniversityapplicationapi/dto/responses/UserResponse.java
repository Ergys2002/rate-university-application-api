package com.app.rateuniversityapplicationapi.dto.responses;

import java.time.LocalDate;

public interface UserResponse {
    String getFirstName();
    String getLastName();
    String getEmail();
    LocalDate getBirdhDate();
    String getPhoneNumber();
    String getProfilePhotoUrl();

}
