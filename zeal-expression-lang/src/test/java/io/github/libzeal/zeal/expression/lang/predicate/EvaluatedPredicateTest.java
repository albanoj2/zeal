package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatedPredicateTest {

    private Result result;
    private String name;
    private Rationale rationale;

    @BeforeEach
    void setUp() {

        result = Result.PASSED;
        name = "foo";
        rationale = SimpleRationale.empty();
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedPredicate(null, Result.PASSED, rationale)
        );
    }

    @Test
    void givenNullResult_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedPredicate("foo", null, rationale)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedPredicate("foo", Result.PASSED, null)
        );
    }

    @Test
    void whenConstruct_thenValidDataReturned() {

        EvaluatedPredicate expression = new EvaluatedPredicate(name, result, rationale);

        assertEquals(result, expression.result());
        assertEquals(name, expression.name());
        assertEquals(rationale, expression.rationale());
        assertTrue(expression.children().isEmpty());
    }
}
