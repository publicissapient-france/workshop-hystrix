package com.xebia.exercice5;

import java.util.concurrent.Future;
import org.junit.Test;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xebia.ShoppingCart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class SimpleShoppingCartCollapserTest {

    @Test
    public void should_test_collapser_call() throws Exception {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<ShoppingCart> cart1 = new SimpleShoppingCartCollapser(1).queue();
        Future<ShoppingCart> cart2 = new SimpleShoppingCartCollapser(2).queue();
        Future<ShoppingCart> cart3 = new SimpleShoppingCartCollapser(3).queue();

        assertThat(cart1.get().getId()).isEqualTo(1);
        assertThat(cart2.get().getId()).isEqualTo(2);
        assertThat(cart3.get().getId()).isEqualTo(3);

        int numExecuted = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size();

        if (numExecuted > 2) {
            fail("some of the commands should have been collapsed");
        }

        int numLogs = 0;
        for (HystrixInvokableInfo<?> command : HystrixRequestLog.getCurrentRequest().getAllExecutedCommands()) {
            numLogs++;

            assertThat(command.getExecutionEvents()).contains(HystrixEventType.COLLAPSED);
            assertThat(command.getExecutionEvents()).contains(HystrixEventType.SUCCESS);
        }

        assertThat(numExecuted).isEqualTo(numLogs);

        context.shutdown();
    }

}