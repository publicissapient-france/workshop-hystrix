package com.xebia.exercice1;

import com.xebia.SucceedingMessageApi;
import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithObservableTest {

    @Test(timeout = 2000)
    public void should_return_observable_message_resolving_to_message_from_api() {
        // given
        SucceedingMessageApi messageApi = new SucceedingMessageApi();
        MessageClientWithObservable messageClient = new MessageClientWithObservable(messageApi);

        // when
        Observable<String> result = messageClient.getMessage("Bob");

        // then
        assertThat(result).isNotNull();
        assertThat(result.toBlocking().single()).isEqualTo("Hello Bob");
    }

}
