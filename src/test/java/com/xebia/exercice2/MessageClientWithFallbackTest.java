package com.xebia.exercice2;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.xebia.FailingMessageApi;
import com.xebia.MessageApi;
import com.xebia.SlowMessageApi;
import com.xebia.SucceedingMessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MessageClientWithFallbackTest {

    @Test
    public void should_return_message_from_api_when_api_is_fine() throws Exception {
        // given
        MessageApi messageApi = new SucceedingMessageApi();
        MessageClientWithFallback messageClientWithFallback = new MessageClientWithFallback(messageApi);

        // when
        String result = messageClientWithFallback.getMessage("Bob");

        // then
        assertThat(result).isEqualTo("Hello Bob");
    }

    @Test
    public void should_return_fallback_message_when_api_raises_an_exception() {
        // given
        MessageApi messageApi = new FailingMessageApi();
        MessageClientWithFallback messageClientWithFallback = new MessageClientWithFallback(messageApi);

        // when
        String result = messageClientWithFallback.getMessage("Bob");

        // then
        assertThat(result).isEqualTo("Unavailable");
    }

    @Test
    public void should_return_fallback_message_when_api_is_too_slow() {
        // given
        final String unexpectedMessage = "MessageClient does not properly timeout when MessageApi is too slow";
        MessageApi messageApi = new SlowMessageApi();
        MessageClientWithFallback messageClientWithFallback = new MessageClientWithFallback(messageApi);

        // when (execute within 500ms timeout)
        HystrixCommand.Setter setter = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Test"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(500)
            );

        String result = new HystrixCommand<String>(setter) {

            @Override
            public String run() throws Exception {
                return messageClientWithFallback.getMessage("Bob");
            }

            @Override
            public String getFallback() {
                return unexpectedMessage;
            }

        }.execute();

        // then
        if (unexpectedMessage.equals(result)) {
            fail(unexpectedMessage);
        } else {
            assertThat(result).isEqualTo("Unavailable");
        }
    }

}
