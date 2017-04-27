package com.xebia.exercice3;

import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCache {

    private final MessageApi messageApi;

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {
        // TODO create and execute an Hystrix command with the setter in parameter and override run method which calls getMessage with the userId

        // TODO In the Hystrix command add the getCacheKey method override which returns a the userId
        // TODO execute the test
    }

}
