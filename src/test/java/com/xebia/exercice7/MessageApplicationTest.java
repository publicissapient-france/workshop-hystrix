package com.xebia.exercice7;

import com.xebia.Exceptions;
import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageApplicationTest {

    @Rule
    public final TestRule testRuleChain = RuleChain
        .outerRule(new SpringBootRule(MessageApplication.class, "--server.port=8080", "--server.tomcat.max-threads=9"))
        .around(new SpringBootRule(SecondRemoteServer.class, "--server.port=8081"))
        .around(new SpringBootRule(FirstRemoteServer.class, "--server.port=8082"));

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    private final RestTemplate restTemplate;

    public MessageApplicationTest() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5_000);
        factory.setConnectTimeout(2_000);
        restTemplate = new RestTemplate(factory);
    }

    @After
    public void teardown() {
        executorService.shutdown();
    }

    @Test
    public void should_return_message_from_second_server_when_first_server_becomes_slow() throws InterruptedException {

        // given
        Stream<Callable<String>> slowTasks = Stream.generate(() -> request("http://localhost:8080/messages/first")).limit(10);

        Stream<Callable<String>> fastTasks = Stream.generate(() -> request("http://localhost:8080/messages/second")).limit(5);

        List<Callable<String>> tasks = Stream.concat(slowTasks, fastTasks).collect(Collectors.toList());

        // when
        List<String> results = executorService.invokeAll(tasks).stream()
            .map(Exceptions.toRuntime(Future::get))
            .collect(Collectors.toList());

        // then
        assertThat(results)
            .areExactly(5, new Condition<>("Message from second remote server"::equals, "All requests to second server should succeed"))
            .areAtLeast(1, new Condition<>("Message from first remote server"::equals, "At least one request to first server should succeed"))
            .areAtLeast(1, new Condition<>("Remote server http://localhost:8081/first is unavailable"::equals, "At least one request to first server should fail"));
    }

    private Callable<String> request(String url) {
        return () -> restTemplate.getForEntity(url, String.class).getBody();
    }

}
