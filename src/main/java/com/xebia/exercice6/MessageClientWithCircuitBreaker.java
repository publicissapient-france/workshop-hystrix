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

    private final Setter setter = Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Message"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreaker"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
            .withCircuitBreakerRequestVolumeThreshold(5)
            .withCircuitBreakerSleepWindowInMilliseconds(1_000));

    public MessageClientWithCircuitBreaker(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

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
