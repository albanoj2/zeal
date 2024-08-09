package io.github.libzeal.zeal.expression.lang.condition;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A condition composed of a name and predicate.
 *
 * @param <T>
 *     The type of the subject being tested by the predicate.
 */
public class SimpleCondition<T> implements Condition<T> {

    private final String name;
    private final Predicate<T> predicate;

    /**
     * Creates a new condition.
     *
     * @param name
     *     The name of the condition.
     * @param predicate
     *     The predicate associated with the condition.
     *
     * @throws NullPointerException
     *     The supplied name or predicate is {@code null}.
     */
    public SimpleCondition(String name, Predicate<T> predicate) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }

    @Override
    public String toString() {
        return "Condition[" + name() + "]";
    }
}
