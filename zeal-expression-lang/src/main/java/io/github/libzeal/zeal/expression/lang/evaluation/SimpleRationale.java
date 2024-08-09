package io.github.libzeal.zeal.expression.lang.evaluation;

import java.util.Objects;
import java.util.Optional;

/**
 * A simple {@link Rationale} implementation that stores strings for the expected value, actual value, and hint (the
 * strings are already constructed without any indirection).
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class SimpleRationale implements Rationale {

    private static final SimpleRationale EMPTY = new SimpleRationale("", "");
    private static final SimpleRationale SKIPPED = new SimpleRationale("(skipped)", "(skipped)");
    private final String expected;
    private final String actual;
    private final String hint;

    /**
     * Creates an empty rationale, where the expected value, actual value, and hint are all blank strings.
     *
     * @return An empty rationale.
     */
    public static SimpleRationale empty() {
        return EMPTY;
    }

    /**
     * Creates a rationale that denotes that an evaluation has been skipped.
     *
     * @return A rationale for a skipped evaluation.
     */
    public static SimpleRationale skipped() {
        return SKIPPED;
    }

    /**
     * Creates a new rationale without a hint.
     *
     * @param expected
     *     The expected value.
     * @param actual
     *     The actual value.
     */
    public SimpleRationale(String expected, String actual) {
        this(expected, actual, null);
    }

    /**
     * Creates a new rationale without a hint.
     *
     * @param expected
     *     The expected value.
     * @param actual
     *     The actual value.
     * @param hint
     *     A hint as to how the expected and actual values should match.
     */
    public SimpleRationale(String expected, String actual, String hint) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
    }

    @Override
    public String expected() {
        return expected;
    }

    @Override
    public String actual() {
        return actual;
    }

    @Override
    public Optional<String> hint() {
        return Optional.ofNullable(hint);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleRationale)) return false;
        final SimpleRationale rationale = (SimpleRationale) o;
        return Objects.equals(expected, rationale.expected) && Objects.equals(actual, rationale.actual) && Objects.equals(hint, rationale.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, hint);
    }
}
