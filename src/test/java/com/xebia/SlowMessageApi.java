package com.xebia;

public class SlowMessageApi implements MessageApi {

    @Override
    public String getMessage(String userName) throws Exception {

        Thread.sleep(10_000);

        return "Hello " + userName;
    }

}
