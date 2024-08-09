package io.github.libzeal.zeal.expression.lang.condition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConditionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleCondition<>(null, s -> true)
        );
    }

    @Test
    void givenNullPredicate_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleCondition<>("foo", null)
        );
    }

    @Test
    void givenValidName_whenName_thenCorrectNameReturned() {

        String name = "foo";

        Condition<Object> condition = new SimpleCondition<>(name, s -> true);

        assertEquals(name, condition.name());
    }

    @Test
    void givenValidName_whenToString_thenCorrectNameReturned() {

        String name = "foo";

        Condition<Object> condition = new SimpleCondition<>(name, s -> true);

        assertEquals("Condition[" + name + "]", condition.toString());
    }

    @Test
    void givenAlwaysTruePredicate_whenTest_thenConditionResolvesTrue() {

        Condition<Object> condition = new SimpleCondition<>("foo", s -> true);

        assertTrue(condition.test(new Object()));
    }

    @Test
    void givenAlwaysFalsePredicate_whenTest_thenConditionResolvesTrue() {

        Condition<Object> condition = new SimpleCondition<>("foo", s -> false);

        assertFalse(condition.test(new Object()));
    }
}
