package com.xebia;

@FunctionalInterface
public interface UserMessageApi {

    String getMessage(String userId) throws Exception;

}
