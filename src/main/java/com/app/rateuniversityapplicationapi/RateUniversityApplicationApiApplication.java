package com.app.rateuniversityapplicationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RateUniversityApplicationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateUniversityApplicationApiApplication.class, args);
    }
}
