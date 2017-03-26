package com.xebia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface UserMessageApi {

    String getMessage(String userId) throws Exception;

    default Map<String, String> getMessage(List<String> userIds) throws Exception {
        Map<String, String> userMessages = new HashMap<>(userIds.size());
        for (String userId : userIds) {
            userMessages.put(userId, getMessage(userId));
        }
        return userMessages;
    }

}
