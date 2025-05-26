package com.laboratorna;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwizardApplication extends Application<DropwizardConfiguration> {

    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DropwizardConfiguration> bootstrap) {}

    @Override
    public void run(DropwizardConfiguration config, Environment env) {
        final UserDAO userDAO = new UserDAO();
        final UserResource resource = new UserResource(userDAO);
        env.jersey().register(resource);
    }
}
