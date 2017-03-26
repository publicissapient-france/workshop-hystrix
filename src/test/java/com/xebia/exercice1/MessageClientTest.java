package com.xebia.exercice1;

import com.xebia.MessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientTest {

    @Test
    public void should_return_message_using_api() {
        // given
        String expectedMessage = "Hello Command World";
        MessageApi messageApi = () -> expectedMessage;
        MessageClient messageClient = new MessageClient(messageApi);

        // when
        String result = messageClient.getMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
