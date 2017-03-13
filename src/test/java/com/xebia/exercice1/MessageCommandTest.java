package com.xebia.exercice1;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MessageCommandTest {

    private static final String expectedMessage = "Hello Command World";

    private static final MessageApi WORKING_MESSAGE_API = () -> expectedMessage;

    private static final MessageApi FAILING_MESSAGE_API = () -> {
        throw new RuntimeException("This shall not pass");
    };

    @Test
    public void should_work_for_command_on_execute() {
        String result = new MessageCommand(WORKING_MESSAGE_API).execute();

        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    public void should_fail_for_command_error_on_execute() {
        try {

            new MessageCommand(FAILING_MESSAGE_API).execute();

            fail("command should have thrown exception when api raises an exception");

        } catch (Exception e) {
            assertThat(e).isInstanceOf(HystrixRuntimeException.class).hasRootCauseInstanceOf(RuntimeException.class);
        }
    }

    @Test
    public void should_work_for_command_on_queue() throws Exception {
        Future<String> futureResult = new MessageCommand(WORKING_MESSAGE_API).queue();

        assertThat(futureResult.get()).isEqualTo(expectedMessage);
    }

    @Test
    public void should_fail_for_command_error_on_queue() {
        try {

            new MessageCommand(FAILING_MESSAGE_API).queue().get();

            fail("command should have thrown exception when api raises an exception");

        } catch (Exception e) {
            assertThat(e).isInstanceOf(ExecutionException.class).hasRootCauseInstanceOf(RuntimeException.class);
        }
    }

    @Test
    public void should_work_for_command_on_observable() throws Exception {
        Observable<String> observableResult = new MessageCommand(WORKING_MESSAGE_API).observe();

        assertThat(observableResult.toBlocking().single()).isEqualTo(expectedMessage);
    }

    @Test
    public void should_fail_for_command_error_on_observable() {
        try {

            new MessageCommand(FAILING_MESSAGE_API).queue().get();

            fail("command should have thrown exception when api raises an exception");

        } catch (Exception e) {
            assertThat(e).isInstanceOf(ExecutionException.class).hasRootCauseInstanceOf(RuntimeException.class);
        }
    }

}
