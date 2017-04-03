package com.xebia.exercice3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCache {

    private final MessageApi messageApi;

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        return new HystrixCommand<String>(Factory.asKey("MessageWithCache")) {

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
