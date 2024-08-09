package io.github.libzeal.zeal.expression.types.core.unary;

/**
 * The abstract base class for all Enum-based expressions.
 * <p/>
 * This base class should be extended when creating expressions for any specialized enums.
 *
 * @param <T>
 *     The type of the enum.
 * @param <B>
 *     The type of the subclass. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>. For more
 *     information, see {@link ObjectUnaryExpression}.
 *
 * @author Justin Albano
 * @see ObjectUnaryExpression
 * @see Enum
 * @since 0.2.0
 */
public abstract class EnumExpression<T extends Enum<T>, B extends EnumExpression<T, B>>
    extends ObjectUnaryExpression<T, B> {

    /**
     * Creates an object expression with the supplied subject. This constructor uses a default name for the expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    protected EnumExpression(T subject) {
        this(subject, "Enum evaluation");
    }

    /**
     * Creates an object expression with the supplied subject and name.
     *
     * @param subject
     *     The subject of the expression.
     * @param name
     *     The name of the expression.
     */
    protected EnumExpression(T subject, String name) {
        super(subject, name);
    }

    /**
     * Adds a predicate to the expression that checks if the ordinal of the subject matches the supplied value.
     *
     * @param ordinal
     *     The ordinal to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B ordinalIs(final int ordinal) {
        return newPredicate(s -> s.ordinal() == ordinal)
            .name("ordinalIs[" + ordinal + "]")
            .expected(ordinal)
            .actual((s, passed) -> String.valueOf(s.ordinal()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the ordinal of the subject does not match the supplied value.
     *
     * @param ordinal
     *     The ordinal to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B ordinalIsNot(final int ordinal) {
        return newPredicate(s -> s.ordinal() != ordinal)
            .name("not[ordinalIs[" + ordinal + "]]")
            .expected((value, passed) -> "not[" + ordinal + "]")
            .actual((s, passed) -> String.valueOf(s.ordinal()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the name of the subject matches the supplied value.
     *
     * @param name
     *     The name to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B nameIs(final String name) {
        return newPredicate(s -> s.name().equals(name))
            .name("nameIs[" + name + "]")
            .expected(name)
            .actual((s, passed) -> s.name())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the name of the subject does not match the supplied value.
     *
     * @param name
     *     The name to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B nameIsNot(final String name) {
        return newPredicate(s -> !s.name().equals(name))
            .name("not[nameIs[" + name + "]]")
            .expected("not[" + name + "]")
            .actual((s, passed) -> s.name())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the name of the subject matches the supplied value, ignoring
     * case.
     *
     * @param name
     *     The name to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B caseInsensitiveNameIs(final String name) {
        return newPredicate(s -> s.name().equalsIgnoreCase(name))
            .name("caseInsensitiveNameIs[" + name + "]")
            .expected("caseInsensitive[" + name + "]")
            .actual((s, passed) -> s.name())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the name of the subject does not match the supplied value,
     * ignoring case.
     *
     * @param name
     *     The name to test the subject against.
     *
     * @return This expression (fluent interface).
     */
    public B caseInsensitiveNameIsNot(final String name) {
        return newPredicate(s -> !s.name().equalsIgnoreCase(name))
            .name("not[caseInsensitiveNameIs[" + name + "]]")
            .expected("not[caseInsensitive[" + name + "]]")
            .actual((s, passed) -> s.name())
            .append();
    }
}