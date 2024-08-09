package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;

/**
 * A generator that creates {@link Rationale} instances based on a supplied subject.
 *
 * @param <T>
 *     The type of the subject.
 */
@FunctionalInterface
public interface RationaleGenerator<T> {

    /**
     * Generates a new rationale based on the supplied subject.
     *
     * @param subject
     *     The subject.
     *
     * @return A rationale based on the supplied subject.
     */
    Rationale generate(T subject, boolean passed);
}
