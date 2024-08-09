package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;

/**
 * An expression used to evaluate whole number {@link Number} instances (such as {@link Long}).
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass.
 *
 * @author Justin Albano
 * @see ObjectUnaryExpression
 * @since 0.2.0
 */
public abstract class BoxedWholeNumberUnaryExpression<T extends Number, E extends ObjectUnaryExpression<T, E>>
    extends BoxedNumberUnaryExpression<T, E> {

    protected BoxedWholeNumberUnaryExpression(final T subject, final String name) {
        super(subject, name);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public E isEven() {
        return newPredicate(l -> l.longValue() % 2 == 0)
            .name("isEven")
            .expected("% 2 := 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is odd.
     *
     * @return This expression (fluent interface).
     */
    public E isOdd() {
        return newPredicate(l -> l.longValue() % 2 != 0)
            .name("isOdd")
            .expected("% 2 != 0")
            .append();
    }
}
