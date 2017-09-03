package com.xebia.exercice1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.xebia.MessageApi;
import rx.Observable;

/**
 * The goal here is to use an HystrixCommand to generate an asynchronous result referenced by an Observable instance.
 * Same as previous, use command to wrap MessageApi calls.
 */
public class MessageClientWithObservable {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;

    MessageClientWithObservable(MessageApi messageApi) {
        this.messageApi = messageApi;
        this.setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Exercise1"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("MessageClientWithObservableCommand"));
    }

    public Observable<String> getMessage(String userName) {

        return new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageApi.getMessage(userName);
            }

        }.observe();

    }

}
