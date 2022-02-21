package io.my.healthcheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthcheckController {
    private final HealthcheckResponse responseBody;
    private final HealthcheckService healthcheckService;

    @Autowired
    public HealthcheckController(HealthcheckResponse responseBody, HealthcheckService healthcheckService) {
        this.responseBody = responseBody;
        this.healthcheckService = healthcheckService;
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<HealthcheckResponse> healthcheck() {
        healthcheckService.healthcheck();
        return ResponseEntity.ok(responseBody);

    }

}
