package com.xebia.exercice6;

import com.xebia.MessageApi;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithCircuitBreakerTest {

    private final AtomicInteger count = new AtomicInteger(0);

    private final MessageApi messageApi = () -> {
        Thread.sleep(500);

        if (count.getAndIncrement() < 6) {
            throw new RuntimeException("Failed");
        }

        return "Success";
    };

    @Test
    public void should_return_message_from_api_using_circuit_breaker_strategy() throws Exception {
        // given
        String expectedFallbackMessage = "Service Unavailable";
        MessageClientWithCircuitBreaker messageClientWithCircuitBreaker = new MessageClientWithCircuitBreaker(messageApi);

        // when 6 failures
        IntStream.range(0, 6).forEach(n ->
            assertThat(messageClientWithCircuitBreaker.getMessage())
                .withFailMessage("Client should return fallback message for Api failures")
                .isEqualTo(expectedFallbackMessage));

        // when circuit is open everything should fallback event tough Api is fine
        IntStream.range(7, 10).forEach(n ->
            assertThat(messageClientWithCircuitBreaker.getMessage())
                .withFailMessage("Circuit should be opened after 5 failures out of 5 requests")
                .isEqualTo(expectedFallbackMessage));

        Thread.sleep(2000);

        // when circuit is closed after a sleep of more than 1s
        assertThat(messageClientWithCircuitBreaker.getMessage())
            .withFailMessage("Circuit should be closed 1s after being opened")
            .isEqualTo("Success");
    }

}
