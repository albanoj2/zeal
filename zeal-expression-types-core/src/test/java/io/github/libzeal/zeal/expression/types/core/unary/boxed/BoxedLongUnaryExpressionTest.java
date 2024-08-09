package io.github.libzeal.zeal.expression.types.core.unary.boxed;

class BoxedLongUnaryExpressionTest extends BoxedWholeNumberUnaryExpressionTest<Long, BoxedLongUnaryExpression> {

    @Override
    protected BoxedLongUnaryExpression expression(final Long value) {
        return new BoxedLongUnaryExpression(value);
    }

    @Override
    protected Long exampleNegativeValue() {
        return -1L;
    }

    @Override
    protected Long exampleZeroValue() {
        return 0L;
    }

    @Override
    protected Long examplePositiveValue() {
        return 1L;
    }

    @Override
    protected Long exampleMaximumValue() {
        return Long.MAX_VALUE;
    }

    @Override
    protected Long exampleMinimumValue() {
        return Long.MIN_VALUE;
    }

    @Override
    protected Long add(final Long a, final short b) {
        return a + b;
    }

    @Override
    protected Long subtract(final Long a, final short b) {
        return a - b;
    }
}
