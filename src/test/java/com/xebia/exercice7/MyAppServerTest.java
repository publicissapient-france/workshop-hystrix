package com.xebia.exercice7;

import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MyAppServerTest {

    @Rule
    public TestRule testRuleChain = RuleChain
        .outerRule(new ServersStarter(MyAppServer.class, "--server.port=8080", "--server.tomcat.max-threads=9"))
        .around(new ServersStarter(SecondRemoteServer.class, "--server.port=8081"))
        .around(new ServersStarter(FirstRemoteServer.class, "--server.port=8082"));

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    private final RestTemplate restTemplate;

    public MyAppServerTest() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5 * 1000);
        factory.setConnectTimeout(2000);
        restTemplate = new RestTemplate(factory);
    }

    @After
    public void teardown() {
        executorService.shutdown();
    }

    @Test
    public void should_return_message_from_second_server_when_first_server_becomes_slow() throws InterruptedException {

        // given
        List<GetMessageTask> slowTasks = Stream.generate(() -> new GetMessageTask("http://localhost:8080/messages/first"))
            .limit(10)
            .collect(Collectors.toList());

        List<GetMessageTask> fastTasks = Stream.generate(() -> new GetMessageTask(
            "http://localhost:8080/messages/second")).limit(5).collect(Collectors.toList());

        List<GetMessageTask> tasks = Stream.concat(slowTasks.stream(), fastTasks.stream()).collect(Collectors.toList());

        // when
        List<Future<String>> futures = executorService.invokeAll(tasks);

        // then
        assertThat(futures).extracting(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).areExactly(5, new Condition<String>() {
            @Override
            public boolean matches(String value) {
                return value.equals("Message from second remote server");
            }
        }).areAtLeast(1, new Condition<String>() {
            @Override
            public boolean matches(String value) {
                return value.equals("Message from first remote server");
            }
        }).areAtLeast(1, new Condition<String>() {
            @Override
            public boolean matches(String value) {
                return value.equals("Remote server http://localhost:8081/first is unavailable");
            }
        });
    }

    private class GetMessageTask implements Callable<String> {

        private String url;

        private GetMessageTask(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            return restTemplate.getForEntity(url, String.class).getBody();
        }

    }

}
