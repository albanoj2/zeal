package io.github.libzeal.zeal.expression.lang;

/**
 * An expression that has a single subject.
 * <p/>
 * The subject of an expression is the object on which the expression is the evaluated. For example, if the expression
 * evaluates whether a value is {@code null}, then the evaluation is executed against the subject of this expression.
 *
 * @param <S>
 *         The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface UnaryExpression<S> extends Expression {

    /**
     * Obtains the subject of the expression.
     *
     * @return The subject of the expression.
     */
    S subject();
}
