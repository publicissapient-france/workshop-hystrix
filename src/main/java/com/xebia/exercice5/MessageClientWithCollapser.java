package com.xebia.exercice5;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

import static java.util.stream.Collectors.toList;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCollapser {

    private final MessageApi messageApi;

    private final class Collapser extends HystrixCollapser<Map<String, String>, String, String> {

        private final String userName;

        public Collapser(String userName) {
            this.userName = userName;
        }

        // TODO override getRequestArgument which returns the userName


        @Override
        public HystrixCommand<Map<String, String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {

            // TODO create an HystrixCommand and override the run method which process the requests and extracts userNames
            // TODO in the run method call and return getMessage with the userNames list

        }

        // TODO override mapResponseToRequests method which has a Map of messages and a CollapsedRequest Collection in parameter
        // TODO iterate the CollapsedRequest Collection and get the argument
        // TODO from the Map get message with the userName
        // TODO set the response

    }

    public MessageClientWithCollapser(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public List<String> getMessage(List<String> userNames) throws Exception {
        // two steps execution required

        // TODO create a list of message futures using collapser queue function

        // TODO create an ArrayList with the userNames

        // TODO collect future results and returns the list

    }

}
