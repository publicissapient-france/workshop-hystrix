package com.xebia.exercice7;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.boot.SpringApplication;

public class SpringBootRule implements TestRule {

    private final Class springBootClass;

    private final String[] springBootArgs;

    SpringBootRule(Class springBootClass, String... springBootArgs) {
        this.springBootClass = springBootClass;
        this.springBootArgs = springBootArgs;
    }

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                try (AutoCloseable ignored = SpringApplication.run(springBootClass, springBootArgs)) {

                    base.evaluate();

                }

            }

        };

    }

}
