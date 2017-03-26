package com.xebia.exercice5;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.UserMessageApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

@SuppressWarnings("WeakerAccess")
public class MessageClientWithCollapser {

    private final UserMessageApi messageApi;

    private final class Collapser extends HystrixCollapser<Map<String, String>, String, String> {

        private final String userId;

        public Collapser(String userId) {
            this.userId = userId;
        }

        @Override
        public String getRequestArgument() {
            return userId;
        }

        @Override
        public HystrixCommand<Map<String, String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {

            return new HystrixCommand<Map<String, String>>(Factory.asKey("Message")) {

                @Override
                public Map<String, String> run() throws Exception {

                    List<String> userIds = requests.stream().map(CollapsedRequest::getArgument).collect(toList());

                    return messageApi.getMessage(userIds);
                }

            };

        }

        @Override
        public void mapResponseToRequests(Map<String, String> messages, Collection<CollapsedRequest<String, String>> requests) {

            requests.forEach(request -> {
                String userId = request.getArgument();
                String message = messages.get(userId);
                request.setResponse(message);
            });

        }

    }

    public MessageClientWithCollapser(UserMessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public List<String> getMessage(List<String> userIds) throws Exception {

        // create a list of message futures using collapser queue function
        List<Future<String>> collapsers = userIds.stream()
            .map(Collapser::new)
            .map(Collapser::queue)
            .collect(toList());

        // get collapsers results (two steps execution required)
        List<String> messages = new ArrayList<>(userIds.size());
        for (Future<String> stringFuture : collapsers) {
            messages.add(stringFuture.get());
        }

        return messages;
    }

}
