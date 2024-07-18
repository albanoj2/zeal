package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano <albano.justin@gmail.com>
 * @since 0.2.0
 */
public interface Expression {

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression.
     */
    EvaluatedExpression evaluate();
}
