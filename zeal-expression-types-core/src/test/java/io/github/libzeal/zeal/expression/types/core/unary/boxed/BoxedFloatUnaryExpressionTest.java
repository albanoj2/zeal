package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

class BoxedFloatUnaryExpressionTest extends BoxedNumberUnaryExpressionTest<Float, BoxedFloatUnaryExpression> {

    @Override
    protected BoxedFloatUnaryExpression expression(final Float value) {
        return new BoxedFloatUnaryExpression(value);
    }

    @Override
    protected Float exampleNegativeValue() {
        return -1.0f;
    }

    @Override
    protected Float exampleZeroValue() {
        return 0.0f;
    }

    @Override
    protected Float examplePositiveValue() {
        return 1.0f;
    }

    @Override
    protected Float exampleMaximumValue() {
        return Float.MAX_VALUE;
    }

    @Override
    protected Float exampleMinimumValue() {
        return Float.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(-0.1f)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(-0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(0.0f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(0.0f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(0.1f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(1.9f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(1.9f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(2.0f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(2.1f)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(2.1f))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isFinite())
                .value(Float.POSITIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(Float.NEGATIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(exampleZeroValue())
                .expectedState(PASSED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(exampleZeroValue()))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isInfinite())
                .value(Float.POSITIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(Float.NEGATIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(exampleZeroValue()))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNaN())
                .value(Float.NaN)
                .expectedState(PASSED)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(ObjectUnaryExpression.stringify(Float.NaN))
                .addTest()
            .newTest((expression, value) -> expression.isNaN())
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(ObjectUnaryExpression.stringify(exampleZeroValue()))
                .addTest();
    }
}
