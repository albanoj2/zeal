package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;

/**
 * A formatter that consumes a subject and provides a string representation. This interface is intended to be used to
 * generate the expected and actual values of an {@link Rationale}.
 *
 * @param <T>
 *     The type of the subject.
 */
@FunctionalInterface
public interface ValueSupplier<T> {

    /**
     * Computes the value based on the supplied subject.
     *
     * @param subject
     *     The subject to compute the value for.
     * @param passed
     *     A flag denoting if the evaluation for which the value is supplied passed or failed. This flag is passed in as
     *     optimization that allows implementations to know if the evaluation passed without having to recompute the
     *     evaluation.
     *
     * @return The computed value.
     */
    String compute(T subject, boolean passed);
}
