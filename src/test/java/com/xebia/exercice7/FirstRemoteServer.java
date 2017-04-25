package com.xebia.exercice7;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FirstRemoteServer {

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
