package io.github.libzeal.zeal.expression.types.core.unary.boxed;

/**
 * An expression used to evaluate {@link Byte} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedByteUnaryExpression extends BoxedWholeNumberUnaryExpression<Byte, BoxedByteUnaryExpression> {

    public BoxedByteUnaryExpression(final Byte subject) {
        super(subject, "Byte expression");
    }

    @Override
    protected Byte zero() {
        return 0;
    }

    @Override
    protected Byte min() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected Byte max() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Byte a, final Byte b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Byte a, final Byte b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Byte a, final Byte b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Byte a, final Byte b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Byte a, final Byte b) {
        return a <= b;
    }
}
