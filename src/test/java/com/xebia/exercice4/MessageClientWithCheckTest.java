package com.xebia.exercice4;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.FailingMessageApi;
import com.xebia.MessageApi;
import com.xebia.SucceedingMessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithCheckTest {

    @Test
    public void should_return_message_from_api_when_parameter_is_not_null() throws Exception {
        // given
        MessageApi messageApi = new SucceedingMessageApi();
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(messageApi);

        // when
        String result = messageClientWithCheck.getMessage("Bob");

        // then
        assertThat(result).isEqualTo("Hello Bob");
    }

    @Test
    public void should_return_fallback_message_when_api_raises_an_error() throws Exception {
        // given
        MessageApi messageApi = new FailingMessageApi();
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(messageApi);

        // when
        String result = messageClientWithCheck.getMessage("Bob");

        // then
        assertThat(result).isEqualTo("Unavailable");
    }

    @Test(expected = HystrixBadRequestException.class)
    public void should_raise_unwrapped_bad_request_when_parameter_is_null() throws Exception {
        // given
        MessageApi messageApi = new SucceedingMessageApi();
        MessageClientWithCheck messageClientWithCheck = new MessageClientWithCheck(messageApi);

        // when
        messageClientWithCheck.getMessage(null);
    }

}
