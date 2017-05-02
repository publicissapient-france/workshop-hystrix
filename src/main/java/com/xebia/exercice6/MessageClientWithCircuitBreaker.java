package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.xebia.MessageApi;

/**
 * The goal here is to use Hystrix circuit breaker capabilities.
 * Circuit breaker allows to stop calling a dependency for some time after multiple fails.
 */
public class MessageClientWithCircuitBreaker {

    private final MessageApi messageApi;

    public MessageClientWithCircuitBreaker(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        Setter setter = Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("MessageWithCircuitBreaker"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreaker"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                .withCircuitBreakerRequestVolumeThreshold(5)
                .withCircuitBreakerSleepWindowInMilliseconds(1_000));

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userId);
            }

            @Override
            public String getFallback() {
                return "Unavailable";
            }

        }.execute();

    }

}
