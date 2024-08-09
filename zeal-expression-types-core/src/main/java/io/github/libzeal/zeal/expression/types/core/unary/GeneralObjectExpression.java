package io.github.libzeal.zeal.expression.types.core.unary;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralObjectExpression<T> extends ObjectUnaryExpression<T, GeneralObjectExpression<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralObjectExpression(T subject) {
        super(subject);
    }
}