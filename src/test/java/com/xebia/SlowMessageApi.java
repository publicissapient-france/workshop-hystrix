package com.xebia;

import java.util.Objects;

public class SlowMessageApi implements MessageApi {

    private final Integer sleepDuration;

    public SlowMessageApi() {
        this(10_000);
    }

    public SlowMessageApi(Integer sleepDuration) {
        this.sleepDuration = Objects.requireNonNull(sleepDuration);
    }

    @Override
    public String getMessage(String userName) throws Exception {

        Thread.sleep(sleepDuration);

        return "Hello " + userName;
    }

}
