package com.app.rateuniversityapplicationapi.exceptions;

public class LecturerNotFoundException extends RuntimeException{
    public LecturerNotFoundException(String message){
        super(message);
    }
}
