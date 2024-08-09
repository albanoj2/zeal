package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

public abstract class BoxedWholeNumberUnaryExpressionTest<T extends Number, E extends BoxedWholeNumberUnaryExpression<T, E>>
    extends BoxedNumberUnaryExpressionTest<T, E> {

    @Override
    protected void customTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value0 = exampleZeroValue();
        final T valueNeg1 = subtract(value0, (short) 1);
        final T valueNeg2 = subtract(value0, (short) 2);
        final T value1 = add(value0, (short) 1);
        final T value2 = add(value0, (short) 2);

        builder.newTest((expression, value) -> expression.isEven())
                .value(value2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(value1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(value0)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(valueNeg1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(valueNeg2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("-2")
                .addTest();
    }

    private void isOddTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value0 = exampleZeroValue();
        final T valueNeg1 = subtract(value0, (short) 1);
        final T valueNeg2 = subtract(value0, (short) 2);
        final T value1 = add(value0, (short) 1);
        final T value2 = add(value0, (short) 2);

        builder.newTest((expression, value) -> expression.isOdd())
                .value(value2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(value1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(value0)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(valueNeg1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(valueNeg2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("-2")
                .addTest();
    }

    protected abstract T add(T a, short b);
    protected abstract T subtract(T a, short b);
}
