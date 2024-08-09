package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;

/**
 * Basic assertions that verify expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Assertions {

    private Assertions() {
    }

    /**
     * Asserts that the supplied expression evaluates to {@link Result#PASSED} with a default message if that
     * evaluation fails. See {@link Assertions#require(UnaryExpression)} for more information.
     *
     * An example usage of this method would be:
     *
     * <pre><code>
     *     public Foo {
     *
     *         private final String bar;
     *
     *         public Foo(String bar) {
     *             this.bar = require(that(bar).isNotNull().isPopulated());
     *         }
     *     }
     * </code></pre>
     *
     * @param expression
     *     The expression to evaluate.
     * @param <T>
     *     The type of the subject.
     *
     * @return The subject of the supplied exception if the evaluation passed.
     *
     * @throws NullPointerException
     *     The supplied expression is {@code null} or the expression returns a {@code null} evaluation (when calling
     *     {@link UnaryExpression#evaluate()}).
     * @throws PreconditionNullPointerException
     *     The evaluation of the supplied expression fails and the subject of the supplied expression is {@code null}.
     * @throws PreconditionIllegalArgumentException
     *     The evaluation of the supplied expression fails and the subject of the supplied expression is not
     *     {@code null}.
     *     
     * @see Assertions#require(UnaryExpression)
     */
    public static <T> T require(final UnaryExpression<T> expression) {
        return Requirement.create().require(expression);
    }

    /**
     * Asserts that the supplied expression evaluates to {@link Result#PASSED}. If the supplied evaluation passes, the
     * subject of the expression is returned.
     * <p/>
     * If the expression fails, then an exception is thrown. The expression thrown on failure depends on the
     * circumstance of the failure:
     * <ol>
     *      <li>The supplied expression is {@code null}: {@link NullPointerException}</li>
     *      <li>The evaluation returned by {@link UnaryExpression#evaluate()} is {@code null}:
     *      {@link NullPointerException}</li>
     *      <li>The expression evaluates to {@link Result#FAILED} and the subject is {@code null}:
     *      {@link PreconditionNullPointerException}</li>
     *      <li>All other circumstances: {@link PreconditionIllegalArgumentException}</li>
     * </ol>
     * An example usage of this method would be:
     *
     * <pre><code>
     *     public Foo {
     *
     *         private final String bar;
     *
     *         public Foo(String bar) {
     *             this.bar = require(that(bar).isNotNull().isPopulated(), "Expected populated bar");
     *         }
     *     }
     * </code></pre>
     *
     * @param expression
     *     The expression to evaluate.
     * @param message
     *     The message to include in the exceptions thrown if the evaluation fails. If the supplied message is
     *     {@code null}, a default message is used.
     * @param <T>
     *     The type of the subject.
     *
     * @return The subject of the supplied exception if the evaluation passed.
     *
     * @throws NullPointerException
     *     The supplied expression is {@code null} or the expression returns a {@code null} evaluation (when calling
     *     {@link UnaryExpression#evaluate()}).
     * @throws PreconditionNullPointerException
     *     The evaluation of the supplied expression fails and the subject of the supplied expression is {@code null}.
     * @throws PreconditionIllegalArgumentException
     *     The evaluation of the supplied expression fails and the subject of the supplied expression is not
     *     {@code null}.
     */
    public static <T> T require(final UnaryExpression<T> expression, final String message) {
        return Requirement.create().require(expression, message);
    }
}
