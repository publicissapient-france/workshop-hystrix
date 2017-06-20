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

public class MessageClientWithThreadPoolTest {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Test
    public void should_use_thread_pool_isolation_to_accept_3_simultaneous_calls() throws InterruptedException {
        // given
        MessageApi messageApi = new SlowMessageApi(400);
        MessageClientWithThreadPool messageClient = new MessageClientWithThreadPool(messageApi);
        List<Callable<String>> callables = IntStream
            .range(0, 4)
            .mapToObj(n -> (Callable<String>) () -> messageClient.getMessage("Bob"))
            .collect(Collectors.toList());

        // when invoking all request at same time
        List<String> results = executorService.invokeAll(callables).stream()
            .map(Exceptions.toRuntime(Future::get))
            .collect(Collectors.toList());

        // then
        assertThat(results)
            .areExactly(2, new Condition<>("Hello Bob"::equals, "First 2 calls should succeed when thread pool is empty"))
            .areExactly(2, new Condition<>("Unavailable"::equals, "Last 2 calls should be rejected when thread pool is full"));
    }

}
