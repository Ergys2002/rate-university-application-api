package com.app.rateuniversityapplicationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
public class RateUniversityApplicationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateUniversityApplicationApiApplication.class, args);
    }
}
