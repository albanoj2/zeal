package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleRationale;

import static java.util.Objects.requireNonNull;

/**
 * A generator that stores a {@link ValueSupplier} for the expected, actual, and hint and computes its rationale based
 * on these suppliers.
 *
 * @param <T>
 *     The type of the subject.
 */
public class SimpleRationaleGenerator<T> implements RationaleGenerator<T> {

    private final ValueSupplier<T> expected;
    private final ValueSupplier<T> actual;
    private final ValueSupplier<T> hint;

    /**
     * Creates a new generator.
     *
     * @param expected
     *     The value supplier for the expected value.
     * @param actual
     *     The value supplier for the actual value.
     * @param hint
     *     The value supplier for the hint.
     *
     * @throws NullPointerException
     *     The supplied expected and actual value suppliers are {@code null}.
     */
    public SimpleRationaleGenerator(final ValueSupplier<T> expected, final ValueSupplier<T> actual, final ValueSupplier<T> hint) {
        this.expected = requireNonNull(expected);
        this.actual = requireNonNull(actual);
        this.hint = hint;
    }

    @Override
    public Rationale generate(final T subject, boolean passed) {

        final String generatedExpected = expected.compute(subject, passed);
        final String generatedActual = actual.compute(subject, passed);
        final String generatedHint = hint == null ? null : hint.compute(subject, passed);

        return new SimpleRationale(generatedExpected, generatedActual, generatedHint);
    }
}
