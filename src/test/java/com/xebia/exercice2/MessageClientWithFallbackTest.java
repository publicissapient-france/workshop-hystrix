package com.xebia.exercice2;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.MessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MessageClientWithFallbackTest {

    @Test
    public void should_return_message_from_api_when_api_is_fine() {
        // given
        String expectedMessage = "Hello World";
        MessageApi fineMessageApi = () -> expectedMessage;
        MessageClientWithFallback messageClientWithFallback = new MessageClientWithFallback(fineMessageApi);

        // when
        String result = messageClientWithFallback.getMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    public void should_return_fallback_message_when_api_is_too_slow() {
        // given
        final String unexpectedMessage = "MessageClient does not properly timeout when MessageApi is too slow";
        MessageApi slowMessageApi = () -> {
            Thread.sleep(100_000);
            return unexpectedMessage;
        };
        MessageClientWithFallback messageClientWithFallback = new MessageClientWithFallback(slowMessageApi);

        // when (execute with 500ms timeout)
        int timeout = 500;
        String result = new HystrixCommand<String>(HystrixCommandGroupKey.Factory.asKey("Test"), timeout) {

            @Override
            public String run() throws Exception {
                return messageClientWithFallback.getMessage();
            }

            @Override
            public String getFallback() {
                return unexpectedMessage;
            }

        }.execute();

        // then
        switch (result) {
            case unexpectedMessage:
                fail(unexpectedMessage);
            default:
                assertThat(result).isEqualTo("Service Unavailable");
        }
    }

}
