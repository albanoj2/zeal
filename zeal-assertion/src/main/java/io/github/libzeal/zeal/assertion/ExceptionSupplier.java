package io.github.libzeal.zeal.assertion;

import java.util.function.Function;

@FunctionalInterface
public interface ExceptionSupplier extends Function<String, Throwable> {

    Throwable create(String message);

    @Override
    default Throwable apply(String message) {
        return create(message);
    }
}
