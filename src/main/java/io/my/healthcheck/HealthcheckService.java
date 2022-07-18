package io.my.healthcheck;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealthcheckService {
    private final HealthcheckResponse responseBody;

    public HealthcheckService(HealthcheckResponse responseBody) {
        this.responseBody = responseBody;
    }

    public void healthcheck() {
        String gatewayHealthcheck = "/healthcheck";
        String userHealthcheck = "/api/user/healthcheck";
        String imageHealthcheck = "/image/healthcheck";
        String calenderHealthcheck = "/api/calender/healthcheck";

        List<Healthcheck> list = new ArrayList<>();

        list.add(healthcheck(true, "dev-gateway", gatewayHealthcheck));
        list.add(healthcheck(true, "dev-user", userHealthcheck));
        list.add(healthcheck(true, "dev-image", imageHealthcheck));
        list.add(healthcheck(true, "dev-calender", calenderHealthcheck));
//        list.add(healthcheck(false, "gateway", gatewayHealthcheck));
//        list.add(healthcheck(false, "user", userHealthcheck));
//        list.add(healthcheck(false, "image", imageHealthcheck));
//        list.add(healthcheck(false, "calender", calenderHealthcheck));

        String lastCheckTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        responseBody.setList(list);
        responseBody.setLastCheckTime(lastCheckTime);

    }

    private Healthcheck healthcheck(Boolean isDev, String service, String healthcheck) {
        if (isDev) {
            String command = "curl -i --negotiate http://mysend.co.kr:8080";
            return healthcheck(command, service, healthcheck);
        } else {
            String command = "curl -i --negotiate http://ec2-3-38-153-18.ap-northeast-2.compute.amazonaws.com:8080";
            return healthcheck(command, service, healthcheck);
        }
    }

    private Healthcheck healthcheck(String command, String service, String healthcheck) {
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
