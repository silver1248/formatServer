package org.sweatshop.format_server;

import org.sweatshop.format_server.health.FormatServerHealthCheck;
import org.sweatshop.format_server.resources.FormatServerResources;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.vavr.jackson.datatype.VavrModule;
import lombok.Generated;

public class FormatServerApplication extends Application<FormatServerConfiguration> {
    @Generated
    public static void main(String[] args) throws Exception {
        new FormatServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<FormatServerConfiguration> bootstrap) {
        bootstrap.getObjectMapper().registerModule(new VavrModule());
    }

    @Override
    public void run(FormatServerConfiguration configuration,
                    Environment environment) {
        final FormatServerResources resource = new FormatServerResources(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                configuration.getFilesConfig()
            );
            final FormatServerHealthCheck healthCheck =
                new FormatServerHealthCheck(configuration.getTemplate());
            environment.healthChecks().register("template", healthCheck);
            environment.jersey().register(resource);
    }


}
