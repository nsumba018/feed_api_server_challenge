package com.nsumba.feed_api.health.service.impl;

import com.nsumba.feed_api.health.service.IHealthService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements IHealthService {

    private final EntityManager entityManager;

    // I save the time the service was created to calculate how long the app has been running
    private final long startTimeMillis = System.currentTimeMillis();

    @Override
    public void checkDatabase() {
        // I run SELECT 1 through JPA because the spec says health is ok only after this query succeeds
        entityManager.createNativeQuery("SELECT 1").getSingleResult();
    }

    @Override
    public long getUptimeSeconds() {
        return (System.currentTimeMillis() - startTimeMillis) / 1000;
    }
}
