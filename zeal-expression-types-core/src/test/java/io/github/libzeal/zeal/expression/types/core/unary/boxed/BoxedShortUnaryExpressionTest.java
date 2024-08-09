package io.github.libzeal.zeal.expression.types.core.unary.boxed;

class BoxedShortUnaryExpressionTest extends BoxedWholeNumberUnaryExpressionTest<Short, BoxedShortUnaryExpression> {

    @Override
    protected BoxedShortUnaryExpression expression(final Short value) {
        return new BoxedShortUnaryExpression(value);
    }

    @Override
    protected Short exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Short exampleZeroValue() {
        return 0;
    }

    @Override
    protected Short examplePositiveValue() {
        return 1;
    }

    @Override
    protected Short exampleMaximumValue() {
        return Short.MAX_VALUE;
    }

    @Override
    protected Short exampleMinimumValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected Short add(final Short a, final short b) {
        return (short) (a + b);
    }

    @Override
    protected Short subtract(final Short a, final short b) {
        return (short) (a - b);
    }
}
