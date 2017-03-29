package com.xebia;

public class FailingMessageApi implements MessageApi {

    @Override
    public String getMessage(String userName) throws Exception {
        throw new Exception("Unable to retrieve message for " + userName);
    }

}
