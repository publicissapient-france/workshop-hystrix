package com.xebia;

import java.util.function.Function;

@FunctionalInterface
public interface Exceptions<T, R> {

    R apply(T t) throws Exception;

    static <T, R> Function<T, R> toRuntime(Exceptions<T, R> exceptions) {
        return t -> {
            try {
                return exceptions.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
