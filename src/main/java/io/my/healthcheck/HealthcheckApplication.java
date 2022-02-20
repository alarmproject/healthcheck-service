package io.my.healthcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HealthcheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthcheckApplication.class, args);
    }

}
