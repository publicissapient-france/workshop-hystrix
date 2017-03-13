package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class MessageCommand extends HystrixCommand<String> {

    private final MessageApi messageApi;

    MessageCommand(MessageApi messageApi) {
        super(HystrixCommandGroupKey.Factory.asKey("CartCommand"));
        this.messageApi = messageApi;
    }

    @Override
    public String run() {
        return messageApi.getMessage();
    }

}
