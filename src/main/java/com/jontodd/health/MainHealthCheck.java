package com.jontodd.health;

import com.codahale.metrics.health.HealthCheck;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jon Todd
 */
public class MainHealthCheck extends HealthCheck {
    private final AtomicLong callCount = new AtomicLong();
    private final AtomicBoolean healthy = new AtomicBoolean(true);

    @Override
    protected Result check() throws Exception {
        long count = callCount.incrementAndGet();
        if (!healthy.get()) {
            return Result.unhealthy("Not healthy. Count: %d", count);
        }
        return Result.healthy("Healthy. Count: %d", count);
    }

    public long getCallCount() {
        return callCount.get();
    }

    public boolean getHealthy() {
        return healthy.get();
    }

    public boolean setHealth(boolean health) {
        this.healthy.set(health);
        return this.healthy.get();
    }
}
