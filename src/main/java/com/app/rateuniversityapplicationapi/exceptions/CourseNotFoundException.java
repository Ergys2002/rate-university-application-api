package com.app.rateuniversityapplicationapi.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String message){
        super(message);
    }
}
