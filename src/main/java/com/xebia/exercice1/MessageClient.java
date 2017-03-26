package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClient {

    private final HystrixCommand<String> command;

    public MessageClient(MessageApi messageApi) {

        this.command = new HystrixCommand<String>(HystrixCommandGroupKey.Factory.asKey("Message")) {
            @Override
            public String run() throws Exception {
                return messageApi.getMessage();
            }
        };

    }

    public String getMessage() {
        return command.execute();
    }

}
