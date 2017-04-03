package com.xebia.exercice1;

import com.xebia.SucceedingMessageApi;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithFutureTest {

    @Test(timeout = 2000)
    public void should_return_future_message_resolving_to_message_from_api() throws ExecutionException, InterruptedException {
        // given
        SucceedingMessageApi messageApi = new SucceedingMessageApi();
        MessageClientWithFuture messageClient = new MessageClientWithFuture(messageApi);

        // when
        Future<String> result = messageClient.getMessage("Bob");

        // then
        assertThat(result).isNotNull();
        assertThat(result.get()).isEqualTo("Hello Bob");
    }

}
