package io.github.libzeal.zeal.expression.lang.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRationaleTest {

    @Test
    void whenEmptyRationale_thenFieldsAreAllBlank() {

        final Rationale rationale = SimpleRationale.empty();

        assertEquals("", rationale.expected());
        assertEquals("", rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenExpectedAndActualValues_whenConstruct_thenFieldsAreAllCorrect() {

        final String expected = "foo";
        final String actual = "bar";

        Rationale rationale = new SimpleRationale(expected, actual);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenExpectedActualAndHint_whenConstruct_thenFieldsAreAllCorrect() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale rationale = new SimpleRationale(expected, actual, hint);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(hint, rationale.hint().get());
    }
}
