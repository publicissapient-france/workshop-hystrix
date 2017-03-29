package com.xebia;

public class SucceedingMessageApi implements MessageApi {

    @Override
    public String getMessage(String userName) throws Exception {
        return "Hello " + userName;
    }
}
