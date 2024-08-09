package io.github.libzeal.zeal.expression.lang.evaluation;

import java.util.Optional;

/**
 * The rationale for why an {@link Evaluation} evaluated to the result that it did. A rationale is made up of at least
 * three parts:
 * <ol>
 *     <li><strong>Expected Value</strong>: The value that the expression should have evaluated to/li>
 *     <li><strong>Actual Value</strong>: The value that the expression actually evaluated to</li>
 *     <li><strong>Hint</strong>: An optional hint that provides additional information about why the actual value
 *     does not match the expected value or what can be done to ensure that the two values match in future
 *     evaluations</li>
 * </ol>
 * <p>
 * Hints are provided for evaluations that fail for a common reason or are very particular about the expected value.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Rationale {

    /**
     * Obtains the expected value for the evaluation.
     *
     * @return The expected value for the evaluation.
     */
    String expected();

    /**
     * Obtains the actual value for the evaluation.
     *
     * @return The actual value for the evaluation.
     */
    String actual();

    /**
     * Obtains the optional hint for the evaluation.
     *
     * @return An {@link Optional} populated with the hint if a hint is available; an empty {@link Optional} otherwise.
     */
    Optional<String> hint();
}
