package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

class BoxedDoubleUnaryExpressionTest extends BoxedNumberUnaryExpressionTest<Double, BoxedDoubleUnaryExpression> {

    @Override
    protected BoxedDoubleUnaryExpression expression(final Double value) {
        return new BoxedDoubleUnaryExpression(value);
    }

    @Override
    protected Double exampleNegativeValue() {
        return -1.0;
    }

    @Override
    protected Double exampleZeroValue() {
        return 0.0;
    }

    @Override
    protected Double examplePositiveValue() {
        return 1.0;
    }

    @Override
    protected Double exampleMaximumValue() {
        return Double.MAX_VALUE;
    }

    @Override
    protected Double exampleMinimumValue() {
        return Double.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Double, BoxedDoubleUnaryExpression> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(-0.1)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(-0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(0.1)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(1.9)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(1.9))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(2.1)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(ObjectUnaryExpression.stringify(2.1))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isFinite())
                .value(Double.POSITIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(Double.NEGATIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(exampleZeroValue())
                .expectedState(PASSED)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(ObjectUnaryExpression.stringify(exampleZeroValue()))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isInfinite())
                .value(Double.POSITIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(Double.NEGATIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(ObjectUnaryExpression.stringify(exampleZeroValue()))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNaN())
                .value(Double.NaN)
                .expectedState(PASSED)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(ObjectUnaryExpression.stringify(Double.NaN))
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
