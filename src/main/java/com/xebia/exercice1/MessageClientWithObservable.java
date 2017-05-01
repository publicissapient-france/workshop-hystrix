package com.xebia.exercice1;

import com.xebia.MessageApi;
import rx.Observable;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithObservable {

    private final MessageApi messageApi;

    public MessageClientWithObservable(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public Observable<String> getMessage(String userId) {

        // TODO create and observe an HystrixCommand which calls getMessage with the userName

        return null;
    }

}
