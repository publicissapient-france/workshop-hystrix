package com.xebia.exercice6;

import com.xebia.Exceptions;
import com.xebia.MessageApi;
import com.xebia.SlowMessageApi;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientWithSemaphoreTest {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Test
    public void should_use_semaphore_isolation_to_accept_2_simultaneous_calls() throws InterruptedException {
        // given
        MessageApi messageApi = new SlowMessageApi(400);
        MessageClientWithSemaphore messageClient = new MessageClientWithSemaphore(messageApi);
        List<Callable<String>> callables = IntStream.range(0, 4)
            .mapToObj(n -> (Callable<String>) () -> messageClient.getMessage("Bob"))
            .collect(Collectors.toList());

        // when invoking all requests at same time
        List<String> results = executorService.invokeAll(callables).stream()
            .map(Exceptions.toRuntime(Future::get))
            .collect(Collectors.toList());

        // then
        assertThat(results)
            .areExactly(2, new Condition<>("Hello Bob"::equals, "First 2 calls should succeed when semaphore has capacity"))
            .areExactly(2, new Condition<>("Unavailable"::equals, "Last 2 calls should fail when semaphore is full"));
    }

}
