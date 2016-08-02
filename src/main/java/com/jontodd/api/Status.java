package com.jontodd.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Todd
 */
public class Status {
    private final long callCount;
    private final boolean healthy;
    private final long responseDelayMillis;

    @JsonCreator
    public Status(@JsonProperty("callCount") long callCount,
                  @JsonProperty("healthy") boolean healthy,
                  @JsonProperty("responseDelayMillis") long responseDelayMillis) {
        this.callCount = callCount;
        this.healthy = healthy;
        this.responseDelayMillis = responseDelayMillis;
    }

    @JsonCreator
    public Status(@JsonProperty("healthy") boolean healthy) {
        this.callCount = 0;
        this.healthy = healthy;
        this.responseDelayMillis = 0;
    }

    public long getCallCount() {
        return callCount;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public long getResponseDelayMillis() {
        return responseDelayMillis;
    }
}
