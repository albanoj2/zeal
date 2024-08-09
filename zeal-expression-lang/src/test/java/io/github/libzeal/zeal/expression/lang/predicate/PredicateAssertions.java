package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PredicateAssertions {

    private PredicateAssertions() {}

    public static void assertHasChildWithName(final Evaluation evaluation, final String name) {
        assertTrue(evaluation.children().stream().anyMatch(e -> e.name().equals(name)));
    }
}
