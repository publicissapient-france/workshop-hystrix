package com.xebia.exercice3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

/**
 * The goal here is to use HystrixCommand cache capabilities.
 */
public class MessageClientWithCache {

    private final MessageApi messageApi;

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userName) {

        return new HystrixCommand<String>(Factory.asKey("MessageWithCache")) {

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
