package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.condition.Condition;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.predicate.ValueSupplier;
import io.github.libzeal.zeal.expression.lang.predicate.unary.ConjunctiveUnaryPredicate;
import io.github.libzeal.zeal.expression.lang.predicate.unary.UnaryPredicate;

import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * The abstract base class for all Object-based (non-primitive) expressions.
 * <p/>
 * This base class should be extended when creating expressions for any specialized class. For example, if creating a
 * {@code StringExpression} class, the {@code StringExpression} class should extend this class, with {@code T} set to
 * {@link String}. An example of a proper extension of this class (to create a {@code StringExpression} for example)
 * would be:
 * <pre><code>class StringExpression extends ObjectExpression<String, StringExpression></code></pre>
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>
 *     . This type allows for the fluent interface methods of this class to return the subtype object, rather than
 *     {@link ObjectUnaryExpression}. If {@link ObjectUnaryExpression} were returned from the fluent interface methods,
 *     then the subclass type would be lost, resulting in the methods available on the subtype to no longer be available
 *     as methods from {@link ObjectUnaryExpression} are called. For example, if a {@code StringExpression} (extending
 *     this class) had a method called {@code includesCharacter(char)}, the following would cause an error:
 *     <pre><code>new StringExpression("foo").isNotNull().includeCharacter('c');</code></pre>
 *     Since the {@code isNotNull()} method would return a {@link ObjectUnaryExpression} object, rather than a
 *     {@code StringExpression}, thus hiding the {@code includesCharacter(char)} method of {@code  StringExpression}.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class ObjectUnaryExpression<T, E extends ObjectUnaryExpression<T, E>> implements UnaryExpression<T> {

    private static final String PREDICATE_SATISFIED = "Predicate satisfied";
    private static final String PREDICATE_UNSATISFIED = "Predicate unsatisfied";
    private static final String CONDITION_SATISFIED = "Condition satisfied";
    private static final String CONDITION_UNSATISFIED = "Condition unsatisfied";
    private static final String ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE = "Always fail: cannot compare to (null) type";
    private static final String EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE = "Evaluation will always " +
        "fail when compared to a (null) type";

    private final T subject;
    private final ConjunctiveUnaryPredicate<T> children;
    private final UnaryPredicateBuilder.BuildableExpression<T, E> buildable;

    /**
     * Creates an object expression with the supplied subject. This constructor uses a default name for the expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    protected ObjectUnaryExpression(T subject) {
        this(subject, "Object[" + stringify(subject) + "] evaluation");
    }

    /**
     * Converts the supplied object into a human-readable string value.
     *
     * @param o
     *     The object to convert.
     *
     * @return A human-readable representation of the supplied object.
     */
    public static String stringify(Object o) {
        return o == null ? "(null)" : o.toString();
    }

    /**
     * Creates an object expression with the supplied subject and name.
     *
     * @param subject
     *     The subject of the expression.
     * @param name
     *     The name of the expression.
     */
    protected ObjectUnaryExpression(T subject, String name) {
        this.subject = subject;
        this.children = new ConjunctiveUnaryPredicate<>(name);
        this.buildable = buildable();
    }

    @SuppressWarnings("unchecked")
    private UnaryPredicateBuilder.BuildableExpression<T, E> buildable() {
        return new UnaryPredicateBuilder.BuildableExpression<T, E>() {

            @Override
            public E prepend(final UnaryPredicate<T> predicate) {
                children.prepend(predicate);
                return (E) ObjectUnaryExpression.this;
            }

            @Override
            public E append(final UnaryPredicate<T> predicate) {
                children.append(predicate);
                return (E) ObjectUnaryExpression.this;
            }
        };
    }

    @Override
    public final T subject() {
        return subject;
    }

    @Override
    public final Evaluation evaluate() {
        return children.evaluate(subject);
    }

    /**
     * Creates a builder for a new evaluation.
     *
     * @param test
     *     The predicate (test) for the evaluation. The supplied predicate can assume that the subject will never be
     *     {@code null} (a {@code null} check will be performed before the predicate is evaluated.
     *
     * @return The evaluation builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    protected final UnaryPredicateBuilder<T, E> newPredicate(Predicate<T> test) {
        return UnaryPredicateBuilder.notNullable(buildable, requireNonNull(test));
    }

    /**
     * Creates a builder for a new evaluation, where the supplied test may receive a {@code null} subject.
     * <p/>
     * For example, if the supplied test is {code}(subject, passed) -> subject.toString().equals("hi")</code>, the value of {@code o} may be
     * {@code null} and should be handled accordingly.
     *
     * @param test
     *     The predicate (test) for the evaluation. The subject supplied to the predicate <em>may be {@code null}</em>.
     *
     * @return The evaluation builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    protected final UnaryPredicateBuilder<T, E> newNullablePredicate(Predicate<T> test) {
        return UnaryPredicateBuilder.nullable(buildable, requireNonNull(test));
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @apiNote This evaluation takes precedent over all other evaluations. For example, if the chain
     *     <code>expression.someFooCheck().isNotNull()</code> were created, the {@code #isNotNull()} evaluation would
     *     be evaluated before the {@code someFooCheck()} evaluation when the expression is evaluated (using the
     *     {@link #evaluate()} method), even though it was second in the chain.
     * @implSpec The precedence of this evaluation is due to the nature of evaluations on objects. Every evaluation,
     *     expect for nullity evaluations (such as "is @{code null}" or "is not {@code null}") or equality checks assume
     *     that the subject of the evaluation is not {@code null}. For example, assume the following chain is called:
     *
     *     <pre><code>expression.toStringIs("foo").isNotNull()</code></pre>
     *     <p>
     *     If the subject of the expression is {@code null}, then the {@code toStringIs("foo")} evaluation will fail,
     *     but not because the {@link Object#toString()} value of the expression is not equal to {@code "foo"}, but
     *     because the subject of the expression is {@code null} (the {@link Object#toString} method could not be called
     *     on the subject, or else a {@link NullPointerException} would be thrown).
     *     <p>
     *     Therefore, if an expression has a {@code #isNotNull()} evaluation, this evaluation is automatically evaluated
     *     first. If the expression does not include {@code #isNotNull()}, then the evaluations are evaluated in the
     *     order in which they are chained on the expression.
     */
    public final E isNotNull() {
        return newNullablePredicate(Objects::nonNull)
            .name("isNotNull")
            .expected("not[(null)]")
            .prepend();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isNull() {
        return newNullablePredicate(Objects::isNull)
            .name("isNull")
            .expected("(null)")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject exactly matches the supplied type.
     * <p/>
     * Note: If the subject is a subtype of the supplied type, this check will fail.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}
     *
     * @return This expression (fluent interface).
     */
    public E isType(final Class<?> type) {

        final UnaryPredicateBuilder<T, E> builder = newPredicate(s -> s.getClass().equals(type));

        if (type == null) {
            builder.name("isType[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isType[" + type + "]")
                .expected(type.toString())
                .hint("Subject should be exactly of type " + type);
        }

        return builder.actual((s, passed) -> s.getClass().toString())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not an exact match of the supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isNotType(final Class<?> type) {

        final UnaryPredicateBuilder<T, E> builder = newPredicate(o -> type != null && !o.getClass().equals(type));

        if (type == null) {
            builder.name("isNotType[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotType[" + type + "]")
                .expected("not[" + type + "]")
                .hint("Subject should be any type other than " + type);
        }

        return builder.actual((s, passed) -> s.getClass().toString())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject matches or is a subtype of the supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code type} is the supplied type and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     type.isAssignableFrom(subject.getClass())
     *                 </code></pre>
     * @see Class#isAssignableFrom(Class)
     */
    public E isInstanceOf(final Class<?> type) {

        final UnaryPredicateBuilder<T, E> builder = newPredicate(s -> type != null && type.isAssignableFrom(s.getClass()));

        if (type == null) {
            builder.name("isInstanceOf[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isInstanceOf[" + type + "]")
                .expected("instanceof[" + type + "]")
                .hint("Subject should be exactly of type " + type + " or a subtype of type " + type);
        }

        return builder.actual((s, passed) -> s.getClass().toString())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not exactly match or is not a subtype of the
     * supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code type} is the supplied type and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     !type.isAssignableFrom(subject.getClass())
     *                 </code></pre>
     * @see Class#isAssignableFrom(Class)
     */
    public E isNotInstanceOf(final Class<?> type) {

        final UnaryPredicateBuilder<T, E> builder = newPredicate(s -> type != null && !type.isAssignableFrom(s.getClass()));

        if (type == null) {
            builder.name("isNotInstanceOf[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotInstanceOf[" + type + "]")
                .expected("not[instanceof[" + type + "]]")
                .hint("Subject should be any type other than " + type + " and not a subtype of type " + type);
        }

        return builder.actual((s, passed) -> s.getClass().toString())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is the same as the supplied object.
     * <p/>
     * This evaluation will pass if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code other} is the supplied object and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     subject == object
     *                 </code></pre>
     */
    public E is(final Object other) {
        return newNullablePredicate(s -> s == other)
            .name("is[" + stringify(other) + "]")
            .expected((s, passed) -> stringify(other))
            .hint("Subject should be identical to " + other + " (using ==)")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not the same as the supplied object.
     * <p/>
     * This evaluation will fail if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code other} is the supplied object and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     subject != object
     *                 </code></pre>
     */
    public E isNot(final Object other) {
        return newNullablePredicate(s -> s != other)
            .name("isNot[" + stringify(other) + "]")
            .expected("not[" + stringify(other) + "]")
            .hint("Subject should not be identical to " + other + " (using !=)")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the supplied object.
     * <p/>
     * This evaluation will pass if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code other} is the supplied object and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     Objects.equals(subject, other);
     *                 </code></pre>
     * @see Objects#equals(Object, Object)
     */
    public E isEqualTo(final Object other) {
        return newNullablePredicate(s -> Objects.equals(s, other))
            .name("isEqualTo[" + stringify(other) + "]")
            .expected(stringify(other))
            .hint("Subject should be equal to " + other + " (using subject.equals(" + other + "))")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not equal to the supplied object.
     * <p/>
     * This evaluation will fail if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code other} is the supplied object and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     !Objects.equals(subject, other);
     *                 </code></pre>
     * @see Objects#equals(Object, Object)
     */
    public E isNotEqualTo(final Object other) {
        return newNullablePredicate(s -> !Objects.equals(s, other))
            .name("isNotEqualTo[" + stringify(other) + "]")
            .expected("not[" + stringify(other) + "]")
            .hint("Subject should be equal to " + other + " (using !subject.equals(" + other + "))")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the hash code of the subject is equal to the supplied hash
     * code.
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code hashCode} is the supplied hash
     *     code and {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     subject.hashCode() == hashCode
     *                 </code></pre>
     */
    public E hashCodeIs(final int hashCode) {
        return newPredicate(s -> s.hashCode() == hashCode)
            .name("hashCode == " + hashCode)
            .expected(hashCode)
            .actual(hashCodeSupplier())
            .append();
    }

    private static <T> ValueSupplier<T> hashCodeSupplier() {
        return (s, passed) -> String.valueOf(s.hashCode());
    }

    /**
     * Adds a predicate to the expression that checks if the hash code of the subject is not equal to the supplied hash
     * code.
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code hashCode} is the supplied hash
     *     code and {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     subject.hashCode() != hashCode
     *                 </code></pre>
     */
    public E hashCodeIsNot(final int hashCode) {
        return newPredicate(s -> s.hashCode() != hashCode)
            .name("hashCode != " + hashCode)
            .expected("not[" + hashCode + "]")
            .actual(hashCodeSupplier())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the {@code toString()} value of the subject is equal to the
     * supplied value.
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code expected} is the supplied string
     *     and {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     subject.toString().equals(expected)
     *                 </code></pre>
     */
    public E toStringIs(final String expected) {
        return newPredicate(s -> s.toString().equals(expected))
            .name("toString().equals(" + expected + ")")
            .expected(expected)
            .hint("Subject's toString() value should equal " + expected + " (using subject.toString().equals(" + expected + "))")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the {@code toString()} value of the subject is not equal to the
     * supplied value.
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code expected} is the supplied string
     *     and {@code subject} is the subject of the expression:
     *     <pre><code>
     *                     !subject.toString().equals(expected)
     *                 </code></pre>
     */
    public E toStringIsNot(final String expected) {
        return newPredicate(s -> !s.toString().equals(expected))
            .name("not[toString().equals(" + expected + ")]")
            .expected("not[" + expected + "]")
            .hint("Subject's toString() value should not equal " + expected + " (using !subject.toString().equals(" + expected + "))")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied predicate is true.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public E satisfies(final Predicate<T> predicate) {
        return newPredicate(predicate)
            .name("predicate")
            .expected(PREDICATE_SATISFIED)
            .actual((o, passed) -> passed ? PREDICATE_SATISFIED : PREDICATE_UNSATISFIED)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied condition is true.
     *
     * @param condition
     *     The condition to test.
     *
     * @return This expression (fluent interface).
     */
    public E satisfies(final Condition<T> condition) {
        return newPredicate(condition)
            .name("condition: " + conditionName(condition))
            .expected(CONDITION_SATISFIED)
            .actual((o, passed) -> passed ? CONDITION_SATISFIED : CONDITION_UNSATISFIED)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied predicate is false.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public E doesNotSatisfy(final Predicate<T> predicate) {
        return newPredicate(o -> !predicate.test(o))
            .name("not[predicate]")
            .expected(PREDICATE_UNSATISFIED)
            .actual((o, passed) -> passed ? PREDICATE_UNSATISFIED : PREDICATE_SATISFIED)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied condition is false.
     *
     * @param condition
     *     The condition to test.
     *
     * @return This expression (fluent interface).
     */
    public E doesNotSatisfy(final Condition<T> condition) {
        return newPredicate(o -> !condition.test(o))
            .name("not[condition: " + conditionName(condition) + "]")
            .expected(CONDITION_UNSATISFIED)
            .actual((o, passed) -> passed ? CONDITION_UNSATISFIED : CONDITION_SATISFIED)
            .append();
    }

    private static String conditionName(final Condition<?> condition) {
        return condition.name() != null && !condition.name().trim().isEmpty() ? condition.name() : "<unnamed>";
    }
}