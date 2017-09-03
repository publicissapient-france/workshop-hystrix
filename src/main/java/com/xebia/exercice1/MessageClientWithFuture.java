package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.xebia.MessageApi;

import java.util.concurrent.Future;

public class MessageClientWithFuture {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;

    MessageClientWithFuture(MessageApi messageApi) {
        this.messageApi = messageApi;
        this.setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Exercise1"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("MessageClientWithFuture"));

    }

    public Future<String> getMessage(String userName) {

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

        }.queue();

    }

}
