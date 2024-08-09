package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpressionTest;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

public class BoxedBooleanUnaryExpressionTest extends ObjectUnaryExpressionTest<Boolean, BoxedBooleanUnaryExpression> {

    @Override
    protected BoxedBooleanUnaryExpression expression(final Boolean value) {
        return new BoxedBooleanUnaryExpression(value);
    }

    @Override
    protected Boolean exampleValue1() {
        return Boolean.TRUE;
    }

    @Override
    protected Boolean exampleValue2() {
        return Boolean.FALSE;
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        isTrueTestCases(builder);
        isFalseTestCases(builder);
    }

    private void isTrueTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isTrue())
                .value(true)
                .expectedState(PASSED)
                .expectedName("isTrue")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isTrue())
                .value(false)
                .expectedState(FAILED)
                .expectedName("isTrue")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest();
    }

    private void isFalseTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isFalse())
                .value(true)
                .expectedState(FAILED)
                .expectedName("isFalse")
                .expectedExpected("false")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isFalse())
                .value(false)
                .expectedState(PASSED)
                .expectedName("isFalse")
                .expectedExpected("false")
                .expectedActual("false")
                .addTest();
    }
}
