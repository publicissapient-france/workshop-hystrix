package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand;
import com.xebia.MessageApi;

/**
 * The goal here is to use Hystrix semaphore isolation feature.
 * Semaphore isolation allows to limit concurrent calls to MessageApi.
 * Commands are executed on current Thread but rejected if semaphore has no more capacity.
 */
public class MessageClientWithSemaphore {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;


    MessageClientWithSemaphore(MessageApi messageApi) {
        this.messageApi = messageApi;

        /*
         TODO:
         Create a Setter instance for an HystrixCommand and configure it to:
         - execute commands using a semaphore strategy
         - allow 2 max concurrent command executions
         */

        this.setter = null;
    }

    public String getMessage(String userId) {

        // TODO create and execute an Hystrix Command with this setter in parameter

        return null;
    }

}
