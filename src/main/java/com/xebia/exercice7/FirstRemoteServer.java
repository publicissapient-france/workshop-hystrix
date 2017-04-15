package com.xebia.exercice7;

import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FirstRemoteServer extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        SpringApplication.run(FirstRemoteServer.class, "--server.port=8081");
    }

    @Override
    protected void after() {
        // stop the server
    }


    @RestController
    @RequestMapping("/first/messages")
    class MessageController {

        @RequestMapping(method = RequestMethod.GET)
        public String getMessage() throws InterruptedException {
            Thread.sleep(3600);
            return "Message from first remote server";
        }

    }

}
