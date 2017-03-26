package com.xebia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface MessageApi {

    String getMessage(String userName) throws Exception;

    default Map<String, String> getMessage(List<String> userNames) throws Exception {
        Map<String, String> userMessages = new HashMap<>(userNames.size());
        for (String userName : userNames) {
            userMessages.put(userName, getMessage(userName));
        }
        return userMessages;
    }

}
