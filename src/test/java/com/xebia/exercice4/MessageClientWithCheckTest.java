package com.xebia.exercice4;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.UserMessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithCheckTest {

    @Test
    public void should_return_message_from_api_when_parameter_is_not_null() throws Exception {
        // given
        UserMessageApi userMessageApi = (userId) -> "Hello " + userId;
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(userMessageApi);

        // when
        String result = messageClientWithCheck.getMessage("User 1");

        // then
        assertThat(result).isEqualTo("Hello User 1");
    }

    @Test
    public void should_return_fallback_message_when_api_raises_an_error() throws Exception {
        // given
        UserMessageApi userMessageApi = (userId) -> {
            throw new Exception("MessageApi is unavailable");
        };
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(userMessageApi);

        // when
        String result = messageClientWithCheck.getMessage("User 1");

        // then
        assertThat(result).isEqualTo("Service Unavailable");
    }

    @Test(expected = HystrixBadRequestException.class)
    public void should_raise_unwrapped_bad_request_when_parameter_is_null() throws Exception {
        // given
        UserMessageApi userMessageApi = (userId) -> "Should not be returned";
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(userMessageApi);

        // when
        messageClientWithCheck.getMessage(null);
    }

}
