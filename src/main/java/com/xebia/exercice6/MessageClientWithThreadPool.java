package com.xebia.exercice6;

import com.netflix.hystrix.HystrixCommand;
import com.xebia.MessageApi;

/**
 * The goal here is to use Hystrix thread isolation feature.
 * Thread isolation limits concurrent calls to MessageApi.
 * Commands are executed on a dedicated dead pool but rejected if thread pool has no more capacity.
 */
public class MessageClientWithThreadPool {

    private final MessageApi messageApi;

    private final HystrixCommand.Setter setter;

    MessageClientWithThreadPool(MessageApi messageApi) {
        this.messageApi = messageApi;

        /*
         TODO:
         Create a Setter instance for an HystrixCommand and configure it to:
         - execute commands using a thread strategy
         - enable 2 concurrent command executions (ThreadPool properties)
         */

        this.setter = null;
    }

    public String getMessage(String userId) {

        // TODO create and execute an Hystrix Command with this setter in parameter

        return null;

    }

}
