package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

import java.util.concurrent.Future;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithFuture {

    private final MessageApi messageApi;

    public MessageClientWithFuture(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public Future<String> getMessage(String userName) {

        return new HystrixCommand<String>(Factory.asKey("Message")) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

        }.queue();

    }

}
