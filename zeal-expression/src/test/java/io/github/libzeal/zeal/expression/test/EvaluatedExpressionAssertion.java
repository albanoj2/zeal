package io.github.libzeal.zeal.expression.test;

import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.Result;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatedExpressionAssertion<T> {

    private final EvaluatedExpression eval;

    public EvaluatedExpressionAssertion(EvaluatedExpression eval) {
        this.eval = eval;
    }

    public void assertStateIs(Result expected) {
        assertEquals(expected, eval.result(), "State is incorrect");
    }

    public void assertNameIs(String name) {
        assertEquals(name, eval.name(), "Name is incorrect");
    }

    public void assertNameMatches(String regex) {
        assertTrue(eval.name().matches(regex), "Name is incorrect: Expected: " + regex + ", actual: " + eval.name());
    }

    public void assertExpectedIs(String expected) {
        assertEquals(expected, eval.rationale().expected(), "Expected value is incorrect");
    }

    public void assertCompoundExpectedValue() {
        assertExpectedIs("All children must pass");
    }

    public void assertActualIs(String actual) {
        assertEquals(actual, eval.rationale().actual(), "Actual value is incorrect");
    }

    public void assertCompoundActualValueIs(int passed, int failed, int skipped) {
        assertActualIs("Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
    }

    public void assertHasNoChildren() {
        assertTrue(eval.children().isEmpty());
    }
}