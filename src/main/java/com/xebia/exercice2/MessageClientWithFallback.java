package com.xebia.exercice2;

import com.xebia.MessageApi;

/**
 * The goal here is to define a fallback in HystrixCommand.
 * Fallback is used whenever command fails (timeout, open circuit, exception raised).
 */
public class MessageClientWithFallback {

    private final MessageApi messageApi;

    public MessageClientWithFallback(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userName) {

        // TODO declare an Hystrix setter command with an execution timeout of 300 milliseconds

        // TODO create and execute an Hystrix command with the setter in parameter and override run method which calls getMessage with the userName
        // TODO execute the first unit test

        // TODO In the Hystrix command override getFallback method and return a Fallback String message (See the associated test to get the string content)
        // TODO execute the second test
        return null;
    }

}
