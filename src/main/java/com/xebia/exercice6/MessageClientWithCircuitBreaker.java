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

    private final Setter setter;

    MessageClientWithCircuitBreaker(MessageApi messageApi) {
        this.messageApi = messageApi;
        this.setter = Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Exercise6"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("MessageClientWithCircuitBreaker"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                .withCircuitBreakerSleepWindowInMilliseconds(2_000) // circuit will close 2 seconds after being opened
                .withCircuitBreakerRequestVolumeThreshold(5) // 5 request are required to start counting errors
                .withCircuitBreakerErrorThresholdPercentage(50) // 50% error rate
                .withMetricsRollingStatisticalWindowInMilliseconds(1000) // in a window of 1 second
            );
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
