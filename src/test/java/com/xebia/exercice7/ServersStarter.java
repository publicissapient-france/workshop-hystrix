package com.xebia.exercice7;

import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

public class ServersStarter extends ExternalResource {

    private final Class springBootClass;

    private final String[] springBootArgs;

    private ConfigurableApplicationContext configurableApplicationContext = null;

    ServersStarter(Class springBootClass, String... springBootArgs) {
        this.springBootClass = springBootClass;
        this.springBootArgs = springBootArgs;
    }

    @Override
    protected void before() {
        configurableApplicationContext = SpringApplication.run(springBootClass, springBootArgs);
    }

    @Override
    protected void after() {
        Optional
            .ofNullable(configurableApplicationContext)
            .ifPresent(ConfigurableApplicationContext::stop);
    }
}
