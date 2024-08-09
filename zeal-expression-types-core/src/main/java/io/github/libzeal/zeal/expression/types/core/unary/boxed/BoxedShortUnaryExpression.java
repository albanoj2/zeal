package io.github.libzeal.zeal.expression.types.core.unary.boxed;

/**
 * An expression used to evaluate {@link Short} instances.
 *
 * @author Justin Albano
 * @implNote Many of the predicates of this expression require unboxing, resulting in some performance loss.
 * @since 0.2.0
 */
public class BoxedShortUnaryExpression extends BoxedWholeNumberUnaryExpression<Short, BoxedShortUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedShortUnaryExpression(final Short subject) {
        super(subject, "Short expression");
    }

    @Override
    protected Short zero() {
        return 0;
    }

    @Override
    protected Short min() {
        return Short.MIN_VALUE;
    }

    @Override
    protected Short max() {
        return Short.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Short a, final Short b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Short a, final Short b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Short a, final Short b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Short a, final Short b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Short a, final Short b) {
        return a <= b;
    }
}
