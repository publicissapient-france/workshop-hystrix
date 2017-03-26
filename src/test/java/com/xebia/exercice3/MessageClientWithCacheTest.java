package com.xebia.exercice3;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xebia.UserMessageApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageClientWithCacheTest {

    private final UserMessageApi userMessageApi = mock(UserMessageApi.class);

    @Before
    public void setup() throws Exception {
        when(userMessageApi.getMessage("user1")).thenReturn("Hello User 1");
        when(userMessageApi.getMessage("user2")).thenReturn("Hello User 2");
        HystrixRequestContext.initializeContext();
    }

    @After
    public void tearDown() {
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }

    @Test
    public void should_return_message_from_api_for_the_first_call_and_from_cache_for_next_calls() throws Exception {
        // given
        MessageClientWithCache messageClientWithCache = new MessageClientWithCache(userMessageApi);

        // when
        String result1 = messageClientWithCache.getMessage("user1");
        String result2 = messageClientWithCache.getMessage("user1");
        String result3 = messageClientWithCache.getMessage("user2");

        // then
        assertThat(result1).isEqualTo("Hello User 1");
        assertThat(result2).isEqualTo("Hello User 1");
        verify(userMessageApi, times(1)).getMessage("user1");

        assertThat(result3).isEqualTo("Hello User 2");
        verify(userMessageApi, times(1)).getMessage("user2");
    }

}
