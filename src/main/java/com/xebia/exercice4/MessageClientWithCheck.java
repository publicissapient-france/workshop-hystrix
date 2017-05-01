package com.xebia.exercice4;

import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCheck {

    private final MessageApi messageApi;

    public MessageClientWithCheck(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {
        // TODO create and execute an Hystrix command with the setter in parameter and override run method which calls getMessage with the userId
        // TODO execute the first unit test

        // TODO in the Hystrix command add the getFallback method override which returns a String (See the associated test to get the string content)
        // TODO execute the second test

        // TODO un the run method check userId nullability : in that case throw an HystrixBadRequestException
        // TODO execute the third unit test
        return null;
    }

}
