package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCircuitBreaker {

    private final MessageApi messageApi;

    private final Setter key = Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Message"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreaker"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
            .withCircuitBreakerRequestVolumeThreshold(5)
            .withCircuitBreakerSleepWindowInMilliseconds(1_000));

    public MessageClientWithCircuitBreaker(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage() {

        return new HystrixCommand<String>(key) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage();
            }

            @Override
            public String getFallback() {
                return "Service Unavailable";
            }

        }.execute();

    }

}
