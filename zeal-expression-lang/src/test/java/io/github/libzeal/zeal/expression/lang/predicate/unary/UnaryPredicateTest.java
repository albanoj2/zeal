package io.github.libzeal.zeal.expression.lang.predicate.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnaryPredicateTest {

    @Test
    void whenName_thenDefaultNameReturned() {

        UnaryPredicate<Object> predicate = o -> null;

        assertEquals("unnamed", predicate.name());
    }
}
