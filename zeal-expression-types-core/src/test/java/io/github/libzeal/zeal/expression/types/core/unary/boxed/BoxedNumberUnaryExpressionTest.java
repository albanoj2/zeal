package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpressionTest;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

public abstract class BoxedNumberUnaryExpressionTest<T extends Number, E extends BoxedNumberUnaryExpression<T, E>>
    extends ObjectUnaryExpressionTest<T, E> {

    @Override
    protected final void extendTestCases(ExpressionTestCaseBuilder<T, E> builder) {
        isEqualToTestCases(builder);
        isNotEqualToTestCases(builder);
        isLessThanTestCases(builder);
        ltTestCases(builder);
        isGreaterThanTestCases(builder);
        gtTestCases(builder);
        isLessThanOrEqualToTestCases(builder);
        lteTestCases(builder);
        isGreaterThanOrEqualToTestCases(builder);
        gteTestCases(builder);
        isMaxValueTestCases(builder);
        isMinValueTestCases(builder);
        isZeroTestCases(builder);
        isNotZeroTestCases(builder);
        isPositiveTestCases(builder);
        isNotPositiveTestCases(builder);
        isNegativeTestCases(builder);
        isNotNegativeTestCases(builder);
        customTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isEqualTo(value1))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("isEqualTo[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedExpected(ObjectUnaryExpression.stringify(value1))
                .expectedActual(ObjectUnaryExpression.stringify(value1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(value1))
                .value(value2)
                .expectedState(FAILED)
                .expectedName("isEqualTo[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedExpected(ObjectUnaryExpression.stringify(value1))
                .expectedActual(ObjectUnaryExpression.stringify(value2))
                .addTest();
    }

    private void isNotEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isNotEqualTo(value1))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("isNotEqualTo[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedExpected("not[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedActual(ObjectUnaryExpression.stringify(value1))
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(value1))
                .value(value2)
                .expectedState(PASSED)
                .expectedName("isNotEqualTo[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedExpected("not[" + ObjectUnaryExpression.stringify(value1) + "]")
                .expectedActual(ObjectUnaryExpression.stringify(value2))
                .addTest();
    }

    private void isLessThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isLessThan(zero))
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(zero))
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(zero))
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void ltTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.lt(zero))
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.lt(zero))
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.lt(zero))
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isLessThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isGreaterThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isGreaterThan(zero))
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(zero))
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(zero))
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void gtTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.gt(zero))
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.gt(zero))
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.gt(zero))
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isLessThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void lteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.lte(zero))
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.lte(zero))
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.lte(zero))
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isGreaterThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void gteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.gte(zero))
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.gte(zero))
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.gte(zero))
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isMaxValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();

        builder.newTest((expression, value) -> expression.isMaxValue())
                .value(max)
                .expectedState(PASSED)
                .expectedName("isMax")
                .expectedExpected(String.valueOf(max))
                .expectedActual(String.valueOf(max))
                .addTest()
            .newTest((expression, value) -> expression.isMaxValue())
                .value(min)
                .expectedState(FAILED)
                .expectedName("isMax")
                .expectedExpected(String.valueOf(max))
                .expectedActual(String.valueOf(min))
                .addTest();
    }

    private void isMinValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();

        builder.newTest((expression, value) -> expression.isMinValue())
                .value(max)
                .expectedState(FAILED)
                .expectedName("isMin")
                .expectedExpected(String.valueOf(min))
                .expectedActual(String.valueOf(max))
                .addTest()
            .newTest((expression, value) -> expression.isMinValue())
                .value(min)
                .expectedState(PASSED)
                .expectedName("isMin")
                .expectedExpected(String.valueOf(min))
                .expectedActual(String.valueOf(min))
                .addTest();
    }

    private void isZeroTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isZero())
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpected(ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isZero")
                .expectedExpected(ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpected(ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isNotZeroTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotZero())
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isNotZero")
                .expectedExpected("not[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotZero())
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isNotZero")
                .expectedExpected("not[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotZero())
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isNotZero")
                .expectedExpected("not[" + ObjectUnaryExpression.stringify(zero) + "]")
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isPositive())
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isPositive")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpected("> " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isNotPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotPositive())
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNegative())
                .value(positive)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(zero)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(negative)
                .expectedState(PASSED)
                .expectedName("isNegative")
                .expectedExpected("< " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    private void isNotNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotNegative())
                .value(positive)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(zero)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(negative)
                .expectedState(FAILED)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + ObjectUnaryExpression.stringify(zero))
                .expectedActual(ObjectUnaryExpression.stringify(negative))
                .addTest();
    }

    @Override
    protected T exampleValue1() {
        return exampleZeroValue();
    }

    @Override
    protected T exampleValue2() {
        return examplePositiveValue();
    }

    protected abstract void customTestCases(final ExpressionTestCaseBuilder<T, E> builder);
    protected abstract T exampleNegativeValue();
    protected abstract T exampleZeroValue();
    protected abstract T examplePositiveValue();
    protected abstract T exampleMaximumValue();
    protected abstract T exampleMinimumValue();
}
