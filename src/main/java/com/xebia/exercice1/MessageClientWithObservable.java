package com.xebia.exercice1;

import com.xebia.MessageApi;
import rx.Observable;

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

        // TODO create and observe an HystrixCommand which calls getMessage with the userName

        return null;
    }

}
