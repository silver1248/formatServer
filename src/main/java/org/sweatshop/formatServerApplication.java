package org.sweatshop;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class formatServerApplication extends Application<formatServerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new formatServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "formatServer";
    }

    @Override
    public void initialize(final Bootstrap<formatServerConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final formatServerConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
