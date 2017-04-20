package com.xebia.exercice7;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecondRemoteServer {

    @RestController
    @RequestMapping("/second/messages")
    class MessageController {

        @RequestMapping(method = RequestMethod.GET)
        public String getMessage() {
            return "Message from second remote server";
        }

    }

}
