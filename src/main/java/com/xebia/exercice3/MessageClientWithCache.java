package com.xebia.exercice3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCache {

    private final MessageApi messageApi;

    private final HystrixCommandGroupKey commandGroupKey = Factory.asKey("MessageWithCache");

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        return new HystrixCommand<String>(commandGroupKey) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userId);
            }

            @Override
            public String getCacheKey() {
                return userId;
            }

        }.execute();

    }

}
