package io.my.healthcheck;

public class Healthcheck {
    private String service;
    private String health;

    public String getHealth() {
        return health;
    }
    public String getService() {
        return service;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setService(String service) {
        this.service = service;
    }
}
