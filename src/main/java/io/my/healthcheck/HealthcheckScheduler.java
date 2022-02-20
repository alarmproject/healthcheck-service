package io.my.healthcheck;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealthcheckScheduler {
    private final HealthcheckResponse responseBody;

    public HealthcheckScheduler(HealthcheckResponse responseBody) {
        this.responseBody = responseBody;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 4)
    public void cronJobSch() {
        String gatewayHealthcheck = "/healthcheck";
        String userHealthcheck = "/api/user/healthcheck";
        String imageHealthcheck = "/api/image/healthcheck";

        List<Healthcheck> list = new ArrayList<>();

        list.add(healthcheck("gateway", gatewayHealthcheck));
        list.add(healthcheck("user", userHealthcheck));
        list.add(healthcheck("image", imageHealthcheck));

        String lastCheckTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        responseBody.setList(list);
        responseBody.setLastCheckTime(lastCheckTime);
    }

    private Healthcheck healthcheck(String service, String healthcheck) {

        String command = "curl -i --negotiate http://mysend.co.kr:8080";
        Healthcheck check = new Healthcheck();

        try {
            Process process = Runtime.getRuntime().exec(command + healthcheck);

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);
            StringBuilder responseStrBuilder = new StringBuilder();
            responseStrBuilder.append('\n');
            String line = new String();

            System.out.println(command + healthcheck);
            while ((line = br.readLine()) != null) {
                responseStrBuilder.append(line).append('\n');
            }

            check.setHealth(responseStrBuilder.toString());
            check.setService(service);

        } catch (IOException e) {
        }

        return check;
    }

}
