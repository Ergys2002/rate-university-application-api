package com.app.rateuniversityapplicationapi.dto.responses;

import lombok.Data;


public interface LecturerResponse {

     String getId();
     String getFirstName();
     String getLastName();
     String getEmail();
     double getRating();
     String getProfilePicture();

}