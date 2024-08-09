package io.github.libzeal.zeal.expression.types.core.unary.test;

import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class ExpressionTestCaseBuilder<T, E extends ObjectUnaryExpression<T, E>> {

    private final List<Arguments> arguments;

    public static <A, B extends ObjectUnaryExpression<A, B>> ExpressionTestCaseBuilder<A, B> newBuilder() {
        return new ExpressionTestCaseBuilder<>();
    }

    public ExpressionTestCaseBuilder() {
        this.arguments = new ArrayList<>();
    }

    public ArgumentBuilder<T, E> newTest(BiFunction<E, T, E> modifier) {
        return new ArgumentBuilder<>(this, modifier);
    }

    private void addArgument(Arguments argument) {
        this.arguments.add(argument);
    }

    public Stream<Arguments> stream() {
        return arguments.stream();
    }

    public static class ArgumentBuilder<T, E extends ObjectUnaryExpression<T, E>> {

        private final ExpressionTestCaseBuilder<T, E> testCaseBuilder;
        private final BiFunction<E, T, E> modifier;
        private String testCaseName;
        private T value;
        private Result state;
        private Function<T, String> name;
        private BiFunction<?, ?, String> expectedValue;
        private BiFunction<?, ?, String> actualValue;
        private BiFunction<?, ?, String> hint;

        public ArgumentBuilder(ExpressionTestCaseBuilder<T, E> testCaseBuilder, BiFunction<E, T, E> modifier) {
            this.testCaseBuilder = testCaseBuilder;
            this.modifier = modifier;
        }

        public ArgumentBuilder<T, E> testCaseName(String name) {
            this.testCaseName = name;
            return this;
        }

        public ArgumentBuilder<T, E> value(T value) {
            this.value = value;
            return this;
        }

        public ArgumentBuilder<T, E> expectedState(Result state) {
            this.state = state;
            return this;
        }

        public ArgumentBuilder<T, E> expectedName(String name) {
            return expectedName(v -> name);
        }

        public ArgumentBuilder<T, E> expectedName(Function<T, String> name) {
            this.name = name;
            return this;
        }

        public ArgumentBuilder<T, E> expectedExpected(String value) {
            return expectedExpected((e, v) -> value);
        }

        public ArgumentBuilder<T, E> expectedExpected(BiFunction<?, ?, String> value) {
            this.expectedValue = value;
            return this;
        }

        public ArgumentBuilder<T, E> expectedActual(String value) {
            return expectedActual((e, v) -> value);
        }

        public ArgumentBuilder<T, E> expectedActual(BiFunction<T, ?, String> value) {
            this.actualValue = value;
            return this;
        }

        public ArgumentBuilder<T, E> expectedHint(String value) {
            return expectedHint((e, v) -> value);
        }

        public ArgumentBuilder<T, E> expectedHint(BiFunction<T, ?, String> hint) {
            this.hint = hint;
            return this;
        }

        private String testCaseName() {

            if (testCaseName == null) {
                return name.apply(value) + ": (value: " + value + ")";
            }
            else {
                return testCaseName;
            }
        }

        private Arguments toArguments() {
            return Arguments.of(testCaseName(), modifier, value, state, name, expectedValue, actualValue, hint);
        }

        public ExpressionTestCaseBuilder<T, E> addTest() {
            this.testCaseBuilder.addArgument(toArguments());
            return testCaseBuilder;
        }
    }
}
