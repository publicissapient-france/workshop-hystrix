package com.xebia.exercice5;

import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xebia.MessageApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.netflix.hystrix.HystrixEventType.COLLAPSED;
import static com.netflix.hystrix.HystrixEventType.SUCCESS;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageApiCollapserTest {

    private final MessageApi messageApi = mock(MessageApi.class);

    @Before
    public void setUp() throws Exception {
        HystrixRequestContext.initializeContext();
    }

    @After
    public void tearDown() throws Exception {
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }

    @Test
    public void should_test_collapser_call() throws Exception {
        // given
        List<String> userIds = asList("Bob", "Alice");
        List<String> expected = asList("Hello Bob", "Hello Alice");
        when(messageApi.getMessage(userIds)).thenReturn(new HashMap<String, String>() {{
            put("Bob", expected.get(0));
            put("Alice", expected.get(1));
        }});

        MessageClientWithCollapser messageClientWithCollapser = new MessageClientWithCollapser(messageApi);

        // when
        List<String> results = messageClientWithCollapser.getMessage(userIds);

        // then
        assertThat(results).isEqualTo(expected);

        Collection<HystrixInvokableInfo<?>> executedCommands = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands();
        assertThat(executedCommands).hasSize(1);
        assertThat(executedCommands.iterator().next().getExecutionEvents()).containsOnly(COLLAPSED, SUCCESS);
    }

}
