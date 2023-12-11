package com.app.rateuniversityapplicationapi.exceptions;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message){
        super(message);
    }
}
