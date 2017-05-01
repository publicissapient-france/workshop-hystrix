package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.xebia.MessageApi;

public class MessageClientWithSemaphore {

    private final MessageApi messageApi;

    public MessageClientWithSemaphore(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        HystrixCommand.Setter commandSetter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupKey"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
                .withExecutionTimeoutEnabled(false)
            );

        return new HystrixCommand<String>(commandSetter) {

            @Override
            protected String run() throws Exception {
                return messageApi.getMessage(userId);
            }

            @Override
            protected String getFallback() {
                return userId + " messages not available";
            }

        }.execute();

    }

}
