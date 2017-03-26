package com.xebia.exercice2;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithFallback {

    private final HystrixCommand<String> command;

    public MessageClientWithFallback(MessageApi messageApi) {

        int timeout = 300;
        HystrixCommandGroupKey commandGroupKey = HystrixCommandGroupKey.Factory.asKey("MessageWithFallback");

        this.command = new HystrixCommand<String>(commandGroupKey, timeout) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage();
            }

            @Override
            public String getFallback() {
                return "Service Unavailable";
            }

        };

    }

    public String getMessage() {
        return command.execute();
    }

}
