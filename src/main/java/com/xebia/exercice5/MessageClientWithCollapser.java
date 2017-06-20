package com.xebia.exercice5;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.xebia.MessageApi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * The goal here is to use HystrixCollapser feature.
 * Collapser allows to gather multiple calls to MessageApi into a unique one.
 */
public class MessageClientWithCollapser {

    private final MessageApi messageApi;

    private final class Collapser extends HystrixCollapser<Map<String, String>, String, String> {

        private final String userName;

        public Collapser(String userName) {
            this.userName = userName;
        }

        @Override
        public String getRequestArgument() {
            // TODO override getRequestArgument which returns the userName for this collapser
            return null;
        }

        @Override
        public HystrixCommand<Map<String, String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {

            // TODO create an HystrixCommand and override the run method which process the requests and extracts userNames
            // TODO in the run method call and return getMessage from messageApi with the userNames list
            return null;
        }

        @Override
        protected void mapResponseToRequests(Map<String, String> batchResponse, Collection<CollapsedRequest<String, String>> requests) {

            // TODO override mapResponseToRequests method which has a Map of messages and a CollapsedRequest Collection in parameter
            // TODO iterate the CollapsedRequest Collection and get the argument
            // TODO from the Map get message with the userName
            // TODO set the response
        }

    }

    MessageClientWithCollapser(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public List<Future<String>> getMessage(List<String> userNames) throws Exception {

        // TODO create a collapser for each userName
        // TODO create and return a list of message futures using collapser queue function
        return null;
    }

}
