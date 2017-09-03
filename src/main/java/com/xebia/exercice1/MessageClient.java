package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.xebia.MessageApi;

public class MessageClient {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;

    MessageClient(MessageApi messageApi) {
        this.messageApi = messageApi;
        this.setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Exercise1"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("MessageClient"));
    }

    public String getMessage(String userName) {

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

        }.execute();

    }

}
