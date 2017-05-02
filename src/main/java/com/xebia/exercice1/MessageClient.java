package com.xebia.exercice1;

import com.xebia.MessageApi;

/**
 * The goal here is simply to implement and use your first HystrixCommand.
 * This command should wrap calls to MessageApi so that Hystrix can add behaviour and control the call.
 */
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
