package com.xebia.exercice4;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.UserMessageApi;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCheck {

    private final UserMessageApi userMessageApi;

    private final HystrixCommandGroupKey commandGroupKey = Factory.asKey("MessageInputCheck");

    public MessageClientWithCheck(UserMessageApi userMessageApi) {
        this.userMessageApi = userMessageApi;
    }

    public String getMessage(String userId) {

        return new HystrixCommand<String>(commandGroupKey) {

            @Override
            public String run() throws Exception {

                if (Objects.isNull(userId)) {
                    throw new HystrixBadRequestException("User ID is null");
                }

                return userMessageApi.getMessage(userId);
            }

            @Override
            public String getFallback() {
                return "Service Unavailable";
            }

        }.execute();

    }

}