package com.nsumba.feed_api.health.service;

public interface IHealthService {

    void checkDatabase();

    long getUptimeSeconds();
}
