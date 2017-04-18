package com.xebia.exercice7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecondRemoteServer {

    protected void start() {
        SpringApplication.run(SecondRemoteServer.class, "--server.port=8082");
    }

    protected void stop() {
        // stop the server
    }


    @RestController
    @RequestMapping("/second/messages")
    class MessageController {

        @RequestMapping(method = RequestMethod.GET)
        public String getMessage() {
            return "Message from second remote server";
        }

    }

}
