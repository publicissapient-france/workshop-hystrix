package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.xebia.MessageApi;

/**
 * The goal here is to use Hystrix circuit breaker capabilities.
 * Circuit breaker allows to stop calling a dependency for some time after multiple fails.
 */
public class MessageClientWithCircuitBreaker {

    private final MessageApi messageApi;

    private final Setter setter;

    MessageClientWithCircuitBreaker(MessageApi messageApi) {
        this.messageApi = messageApi;

        /*
         TODO:
         Create a Setter instance for an HystrixCommand and configure it to:
         - open circuit when 50% calls fail in a window of 1 second (with at least 5 requests in this window)
         - close circuit 2 seconds after being opened
         */

        this.setter = null;
    }

    public String getMessage(String userId) {

        // TODO create and execute an Hystrix Command with this setter in parameter

        return null;

    }

}
