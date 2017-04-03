package com.xebia.exercice4;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.MessageApi;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCheck {

    private final MessageApi messageApi;

    public MessageClientWithCheck(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userId) {

        return new HystrixCommand<String>(Factory.asKey("MessageWithCheck")) {

            @Override
            public String run() throws Exception {

                if (Objects.isNull(userId)) {
                    throw new HystrixBadRequestException("User ID is null");
                }

                return messageApi.getMessage(userId);
            }

            @Override
            public String getFallback() {
                return "Unavailable";
            }

        }.execute();

    }

}
