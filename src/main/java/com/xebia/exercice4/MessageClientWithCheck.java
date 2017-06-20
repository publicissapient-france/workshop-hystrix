package com.xebia.exercice4;

import com.xebia.MessageApi;

/**
 * The goal here is to illustrate how HystrixCommand can raise exception to caller and not return fallback.
 */
public class MessageClientWithCheck {

    private final MessageApi messageApi;

    public MessageClientWithCheck(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userName) {

        // TODO create and execute an Hystrix command and override run method which calls getMessage with the userName
        // TODO execute the first unit test

        // TODO in the Hystrix command override getFallback method which returns a String (See the associated test to get the string content)
        // TODO execute the second test

        // TODO in the run method check userName nullability : in that case throw an HystrixBadRequestException
        // TODO execute the third unit test
        return null;
    }

}
