package com.xebia.exercice6;

import com.xebia.MessageApi;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithCircuitBreakerTest {

    private final AtomicInteger count = new AtomicInteger(0);

    private final MessageApi messageApi = userName -> {

        if (count.getAndIncrement() < 5) {
            throw new RuntimeException("Failed");
        }

        return "Hello " + userName;
    };

    @Test
    public void should_return_message_from_api_using_circuit_breaker_strategy() throws Exception {
        // given
        MessageClientWithCircuitBreaker messageClientWithCircuitBreaker = new MessageClientWithCircuitBreaker(messageApi);

        // when 10 failures in less than 1 second
        assertThat(IntStream.range(0, 10)
            .mapToObj(n -> messageClientWithCircuitBreaker.getMessage("Bob"))
            .collect(Collectors.toList()))
            .areExactly(5, new Condition<>("Unavailable"::equals, "Client should return fallback message for Api failures"))
            .areExactly(5, new Condition<>("Hello Bob"::equals, "Client should return api message for Api successes"));

        Thread.sleep(1_000);

        // when circuit is open everything should fallback even tough Api is fine
        assertThat(IntStream.range(10, 20)
            .mapToObj(n -> messageClientWithCircuitBreaker.getMessage("Alice"))
            .collect(Collectors.toList()))
            .areExactly(10, new Condition<>("Unavailable"::equals, "Circuit should be opened after 5 failures out of 10 requests in less than 1 second"));

        Thread.sleep(2_000);

        // when circuit is closed after a sleep of more than 2s
        assertThat(messageClientWithCircuitBreaker.getMessage("Eve"))
            .withFailMessage("Circuit should be closed 1s after being opened")
            .isEqualTo("Hello Eve");
    }

}
