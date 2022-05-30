package io.my.healthcheck;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HealthcheckScheduler {
    private final HealthcheckService healthcheckService;

    public HealthcheckScheduler(HealthcheckService healthcheckService) {
        this.healthcheckService = healthcheckService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void cronJobSch() {
        healthcheckService.healthcheck();
    }


}
