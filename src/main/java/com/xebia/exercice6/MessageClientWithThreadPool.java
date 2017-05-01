package com.xebia.exercice6;

import com.netflix.hystrix.*;
import com.xebia.MessageApi;

public class MessageClientWithThreadPool {

    private final MessageApi messageApi;

    public MessageClientWithThreadPool(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        HystrixCommand.Setter setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupKey"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PoolThread"))
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withExecutionTimeoutEnabled(false)
            );

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userId);
            }

            @Override
            public String getFallback() {
                getExecutionException().printStackTrace();
                return userId + " messages not available";
            }

        }.execute();

    }

}
