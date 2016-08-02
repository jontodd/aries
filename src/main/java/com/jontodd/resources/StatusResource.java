package com.jontodd.resources;

import com.codahale.metrics.annotation.Timed;
import com.jontodd.api.Status;
import com.jontodd.health.MainHealthCheck;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

/**
 * @author Jon Todd
 */
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {
    private MainHealthCheck healthCheck;
    private Random random = new Random();

    public StatusResource(MainHealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    @GET
    @Timed
    public Response get(@QueryParam("minResponseDelayMillis") int minResponseDelayMillis,
                      @QueryParam("maxResponseDelayMillis") int maxResponseDelayMillis) {
        if (minResponseDelayMillis < 0) {
            minResponseDelayMillis = 0;
        }

        if (maxResponseDelayMillis < 0) {
            maxResponseDelayMillis = 0;
        }

        if (maxResponseDelayMillis < minResponseDelayMillis) {
            maxResponseDelayMillis = minResponseDelayMillis;
        }

        // Compute a random delay within range and wait for it
        int delayMillis = random.nextInt(maxResponseDelayMillis - minResponseDelayMillis + 1) + minResponseDelayMillis;
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        if (healthCheck.getHealthy()) {
            return Response.ok(new Status(healthCheck.getCallCount(), healthCheck.getHealthy(), delayMillis)).build();
        } else {
            return Response
                    .serverError()
                    .entity(new Status(healthCheck.getCallCount(), healthCheck.getHealthy(), delayMillis))
                    .build();
        }

    }

    @POST
    @Timed
    public Status changeHealth(Status status) {
        healthCheck.setHealth(status.isHealthy());
        return status;
    }
}
