package com.xebia.exercice3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.xebia.MessageApi;

/**
 * The goal here is to use HystrixCommand cache capabilities.
 */
public class MessageClientWithCache {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;

    MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
        this.setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Exercise3"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("MessageClientWithCache"));
    }

    public String getMessage(String userName) {

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

            @Override
            public String getCacheKey() {
                return userName;
            }

        }.execute();

    }

}
