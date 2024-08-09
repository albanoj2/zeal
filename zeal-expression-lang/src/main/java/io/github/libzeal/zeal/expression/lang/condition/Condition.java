package io.github.libzeal.zeal.expression.lang.condition;

import java.util.function.Predicate;

/**
 * A named predicate that represents a desired condition that an expression should satisfy. These conditions are usually
 * provided as arguments to a {@code satisfies} method.
 *
 * @param <T>
 *     The type of the subject being tested.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Condition<T> extends Predicate<T> {

    /**
     * Obtains the name of the condition.
     *
     * @return The name of the condition.
     */
    String name();

    /**
     * Creates the logical negation of the supplied condition.
     *
     * @param wrapped
     *     The condition to wrap.
     * @param <T>
     *     The type of the subject.
     *
     * @return The logical negation of the supplied condition.
     */
    static <T> Condition<T> not(Condition<T> wrapped) {
        return new Condition<T>() {

            @Override
            public boolean test(final T t) {
                return !wrapped.test(t);
            }

            @Override
            public String name() {
                return "not[" + wrapped.name() + "]";
            }
        };
    }
}
