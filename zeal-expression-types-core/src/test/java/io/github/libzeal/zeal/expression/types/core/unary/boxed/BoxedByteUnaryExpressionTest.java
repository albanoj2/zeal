package io.github.libzeal.zeal.expression.types.core.unary.boxed;

class BoxedByteUnaryExpressionTest extends BoxedWholeNumberUnaryExpressionTest<Byte, BoxedByteUnaryExpression> {

    @Override
    protected BoxedByteUnaryExpression expression(final Byte value) {
        return new BoxedByteUnaryExpression(value);
    }

    @Override
    protected Byte exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Byte exampleZeroValue() {
        return 0;
    }

    @Override
    protected Byte examplePositiveValue() {
        return 1;
    }

    @Override
    protected Byte exampleMaximumValue() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected Byte exampleMinimumValue() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected Byte add(final Byte a, final short b) {
        return (byte) (a + b);
    }

    @Override
    protected Byte subtract(final Byte a, final short b) {
        return (byte) (a - b);
    }
}
