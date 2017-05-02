package com.xebia.exercice3;

import com.xebia.MessageApi;

/**
 * The goal here is to use HystrixCommand cache capabilities.
 */
public class MessageClientWithCache {

    private final MessageApi messageApi;

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {
        // TODO create and execute an Hystrix command with the setter in parameter and override run method which calls getMessage with the userId

        // TODO In the Hystrix command add the getCacheKey method override which returns a the userId
        // TODO execute the test
        return null;
    }

}
