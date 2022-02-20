package io.my.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthcheckController {
    private final HealthcheckResponse responseBody;

    public HealthcheckController(HealthcheckResponse responseBody) {
        this.responseBody = responseBody;
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<HealthcheckResponse> healthcheck() {
        return ResponseEntity.ok(responseBody);

    }

}
