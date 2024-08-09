package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;

/**
 * A formatter that consumes an evaluation and provides a string-based illustration of the evaluation.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface EvaluationFormatter {

    /**
     * Formats the supplied evaluation.
     *
     * @param evaluation
     *     The evaluation to format.
     *
     * @return The formatted evaluation.
     */
    String format(Evaluation evaluation);
}
