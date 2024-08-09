package io.github.libzeal.zeal.expression.types.core.unary.boxed;

/**
 * An expression used to evaluate {@link Double} instances.
 *
 * @author Justin Albano
 * @implNote Many of the predicates of this expression require unboxing, resulting in some performance loss.
 * @since 0.2.0
 */
public class BoxedDoubleUnaryExpression extends BoxedNumberUnaryExpression<Double, BoxedDoubleUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedDoubleUnaryExpression(final Double subject) {
        super(subject, "Double expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal within the supplied delta.
     *
     * @param value
     *     The value to compare to the subject.
     * @param delta
     *     The delta used to compare the expected value to the subject.
     *
     * @return This expression (fluent interface).
     *
     * @implSpec The formula used to evaluate the predicate is:
     *
     *     <pre><code>abs(subject - value) <= delta</code></pre>
     */
    public BoxedDoubleUnaryExpression isEqualTo(final Double value, final Double delta) {
        return newPredicate(d -> Math.abs(d - value) <= delta)
            .name("isEqualTo[" + value + " +/- " + delta + "]")
            .expected(value + " +/- " + delta)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is finite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleUnaryExpression isFinite() {
        return newPredicate(Double::isFinite)
            .name("isFinite")
            .expected("finite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleUnaryExpression isInfinite() {
        return newPredicate(l -> l.isInfinite())
            .name("isInfinite")
            .expected("infinite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a number (NaN).
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleUnaryExpression isNaN() {
        return newPredicate(l -> l.isNaN())
            .name("isNaN")
            .expected("NaN")
            .append();
    }

    @Override
    protected Double zero() {
        return 0.0;
    }

    @Override
    protected Double min() {
        return Double.MIN_VALUE;
    }

    @Override
    protected Double max() {
        return Double.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Double a, final Double b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Double a, final Double b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Double a, final Double b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Double a, final Double b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Double a, final Double b) {
        return a <= b;
    }
}
