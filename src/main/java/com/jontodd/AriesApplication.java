package com.jontodd;

import com.jontodd.health.MainHealthCheck;
import com.jontodd.resources.StatusResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AriesApplication extends Application<AriesConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AriesApplication().run(args);
    }

    @Override
    public String getName() {
        return "Aries";
    }

    @Override
    public void initialize(final Bootstrap<AriesConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AriesConfiguration configuration,
                    final Environment environment) {
        MainHealthCheck healthCheck = new MainHealthCheck();
        StatusResource statusResource = new StatusResource(healthCheck);

        environment.jersey().register(statusResource);
        environment.healthChecks().register("countingHealthcheck", healthCheck);
    }

}
