package com.xebia.exercice3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.UserMessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCache {

    private final UserMessageApi userMessageApi;

    private final HystrixCommandGroupKey commandGroupKey = Factory.asKey("MessageWithCache");

    public MessageClientWithCache(UserMessageApi userMessageApi) {
        this.userMessageApi = userMessageApi;
    }

    public String getMessage(String userId) {

        return new HystrixCommand<String>(commandGroupKey) {

            @Override
            public String run() throws Exception {
                return userMessageApi.getMessage(userId);
            }

            @Override
            public String getCacheKey() {
                return userId;
            }

        }.execute();

    }

}
