package com.xebia.exercice1;

import java.util.concurrent.Future;
import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithFuture {

    private final MessageApi messageApi;

    public MessageClientWithFuture(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public Future<String> getMessage(String userName) {
        // TODO create and queue an HystrixCommand which calls getMessage with the userName
    }

}
