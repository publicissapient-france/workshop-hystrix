package com.xebia.exercice1;

import com.xebia.SucceedingMessageApi;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientTest {

    @Test
    public void should_return_message_using_api() {
        // given
        SucceedingMessageApi messageApi = new SucceedingMessageApi();
        MessageClient messageClient = new MessageClient(messageApi);

        // when
        String result = messageClient.getMessage("Bob");

        // then
        assertThat(result).isEqualTo("Hello Bob");
    }

}
