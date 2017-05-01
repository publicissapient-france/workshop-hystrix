package com.xebia.exercice1;

import com.xebia.MessageApi;

@SuppressWarnings("WeakerAccess")
public class MessageClient {

    private final MessageApi messageApi;

    public MessageClient(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userName) {
        // TODO create and execute an HystrixCommand which calls getMessage with the userName
        return null;
    }

}
