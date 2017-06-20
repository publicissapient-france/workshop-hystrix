package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;
import rx.Observable;

import java.util.concurrent.Future;
/**
 * The goal here is to use an HystrixCommand to generate an asynchronous result referenced by an Observable instance.
 * Same as previous, use command to wrap MessageApi calls.
 */
public class MessageClientWithObservable {

    private final MessageApi messageApi;

    public MessageClientWithObservable(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public Observable<String> getMessage(String userName) {

        return new HystrixCommand<String>(Factory.asKey("Message")) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

        }.observe();

    }

}
