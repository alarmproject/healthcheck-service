package io.my.healthcheck;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthcheckResponse {
    private List<Healthcheck> list;
    private String lastCheckTime;

    public List<Healthcheck> getList() {
        return list;
    }
    public void setList(List<Healthcheck> list) {
        this.list = list;
    }
    public String getLastCheckTime() {
        return lastCheckTime;
    }
    public void setLastCheckTime(String lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }
}
