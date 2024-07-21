package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.SubjectExpression;
import io.github.libzeal.zeal.expression.condition.Condition;
import io.github.libzeal.zeal.expression.evaluation.*;

import java.util.Objects;
import java.util.function.*;

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
 * @param <B>
 *     The type of the subclass. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>
 *     . This type allows for the fluent interface methods of this class to return the subtype object, rather than
 *     {@link ObjectExpression}. If {@link ObjectExpression} were returned from the fluent interface methods, then the
 *     subclass type would be lost, resulting in the methods available on the subtype to no longer be available as
 *     methods from {@link ObjectExpression} are called. For example, if a {@code StringExpression} (extending this
 *     class) had a method called {@code includesCharacter(char)}, the following would cause an error:
 *     <pre><code>new StringExpression("foo").isNotNull().includeCharacter('c');</code></pre>
 *     Since the {@code isNotNull()} method would return a {@link ObjectExpression} object, rather than a
 *     {@code StringExpression}, thus hiding the {@code includesCharacter(char)} method of {@code  StringExpression}.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class ObjectExpression<T, B extends ObjectExpression<T, B>> implements SubjectExpression<T> {

    private static final String PREDICATE_SATISFIED = "Predicate satisfied";
    private static final String PREDICATE_UNSATISFIED = "Predicate unsatisfied";
    private static final String CONDITION_SATISFIED = "Condition satisfied";
    private static final String CONDITION_UNSATISFIED = "Condition unsatisfied";
    private static final String ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE = "Always fail: cannot compare to (null) type";
    private static final String EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE = "Evaluation will always " +
        "fail when compared to a (null) type";

    private final T subject;
    private final CompoundEvaluation<T> children;

    /**
     * Creates an object expression with the supplied subject. This constructor uses a default name for the expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    protected ObjectExpression(T subject) {
        this(subject, "Object[" + stringOf(subject) + "] evaluation");
    }

    private static String stringOf(Object o) {
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
    protected ObjectExpression(T subject, String name) {
        this.subject = subject;
        this.children = new CompoundEvaluation<>(name);
    }

    @Override
    public final T subject() {
        return subject;
    }

    @Override
    public final EvaluatedExpression evaluate() {
        return children.evaluate(subject, false);
    }

    /**
     * Appends the supplied evaluation to the chain of evaluations. Appended evaluations will be evaluated in the order
     * in which they are appended (first in, first evaluated). The only exception to this rule is a {@link #isNotNull()}
     * evaluation. If a {@link #isNotNull()} is appended to the chain, it is automatically evaluated first.
     *
     * @param evaluation
     *     The evaluation to append.
     *
     * @return This expression (fluent interface).
     */
    @SuppressWarnings("unchecked")
    protected final B appendEvaluation(Evaluation<T> evaluation) {

        children.append(evaluation);

        return (B) this;
    }

    /**
     * A builder used to create {@link Evaluation} objects.
     */
    protected class EvaluationBuilder {

        private final boolean nullable;
        private final Predicate<T> test;
        private String name = "<unnamed>";
        private ValueFormatter<T> expected = s -> "<not set>";
        private ValueFormatter<T> actual = ObjectExpression::stringOf;
        private ValueFormatter<T> hint = null;

        private EvaluationBuilder(boolean nullable, Predicate<T> test) {
            this.nullable = nullable;
            this.test = test;
        }

        /**
         * Sets the name of the evaluation.
         *
         * @param name
         *     The name of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedValue(ValueFormatter<T> expected) {
            this.expected = expected;
            return this;
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedValue(String expected) {
            return expectedValue(ValueFormatter.of(expected));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedValue(long expected) {
            return expectedValue(String.valueOf(expected));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedValue(double expected) {
            return expectedValue(String.valueOf(expected));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedValue(Supplier<String> expected) {
            return expectedValue(ValueFormatter.of(expected));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedLongValue(ToIntFunction<T> expected) {
            return expectedValue((T o) -> String.valueOf(expected.applyAsInt(o)));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedLongValue(ToLongFunction<T> expected) {
            return expectedValue((T o) -> String.valueOf(expected.applyAsLong(o)));
        }

        /**
         * Sets the expected valued of the evaluation.
         *
         * @param expected
         *     The expected value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder expectedDoubleValue(ToDoubleFunction<T> expected) {
            return expectedValue((T o) -> String.valueOf(expected.applyAsDouble(o)));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualValue(ValueFormatter<T> actual) {
            this.actual = actual;
            return this;
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualValue(String actual) {
            return actualValue(ValueFormatter.of(actual));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualValue(Supplier<T> actual) {
            return actualValue(ValueFormatter.of(actual));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualIntValue(ToIntFunction<T> actual) {
            return actualValue((T o) -> String.valueOf(actual.applyAsInt(o)));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualLongValue(ToLongFunction<T> actual) {
            return actualValue((T o) -> String.valueOf(actual.applyAsLong(o)));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualDoubleValue(ToDoubleFunction<T> actual) {
            return actualValue((T o) -> String.valueOf(actual.applyAsDouble(o)));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualValue(long actual) {
            return actualValue(String.valueOf(actual));
        }

        /**
         * Sets the actual valued of the evaluation.
         *
         * @param actual
         *     The actual value of the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder actualValue(double actual) {
            return actualValue(String.valueOf(actual));
        }

        /**
         * Sets the hint for the evaluation.
         *
         * @param hint
         *     The hint for the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder hint(ValueFormatter<T> hint) {
            this.hint = hint;
            return this;
        }

        /**
         * Sets the hint for the evaluation.
         *
         * @param hint
         *     The hint for the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder hint(String hint) {
            return hint(ValueFormatter.of(hint));
        }

        /**
         * Sets the hint for the evaluation.
         *
         * @param hint
         *     The hint for the evaluation.
         *
         * @return This builder (fluent interface).
         */
        public EvaluationBuilder hint(Supplier<String> hint) {
            return hint(ValueFormatter.of(hint));
        }

        /**
         * Appends the evaluation being built to the evaluation chain.
         *
         * @return This builder (fluent interface).
         */
        public B append() {
            return appendEvaluation(createEvaluation());
        }

        @SuppressWarnings("unchecked")
        private B prepend() {
            children.prepend(createEvaluation());
            return (B) ObjectExpression.this;
        }

        private Evaluation<T> createEvaluation() {

            final ReasonGenerator<T> generator = new ReasonGenerator<>(expected, actual, hint);

            if (nullable) {
                return TerminalEvaluation.ofNullable(name, test, generator);
            } else {
                return TerminalEvaluation.of(name, test, generator);
            }
        }
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
    protected final EvaluationBuilder newEvaluation(Predicate<T> test) {
        return new EvaluationBuilder(false, requireNonNull(test));
    }

    /**
     * Creates a builder for a new evaluation, where the supplied test may receive a {@code null} subject.
     * <p/>
     * For example, if the supplied test is {code}o -> o.toString().equals("hi")</code>, the value of {@code o} may be
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
    protected final EvaluationBuilder newNullableEvaluation(Predicate<T> test) {
        return new EvaluationBuilder(true, requireNonNull(test));
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is not {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @apiNote This evaluation takes precedent over all other evaluations. For example, if the chain
     *     <code>expression.someFooCheck().isNotNull()</code> were created, the {@link #isNotNull()} evaluation would
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
     *     Therefore, if an expression has a {@link #isNotNull()} evaluation, this evaluation is automatically evaluated
     *     first. If the expression does not include {@link #isNotNull()}, then the evaluations are evaluated in the
     *     order in which they are chained on the expression.
     */
    public final B isNotNull() {
        return newNullableEvaluation(Objects::nonNull)
            .name("isNotNull")
            .expectedValue("not[(null)]")
            .prepend();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public B isNull() {
        return newNullableEvaluation(Objects::isNull)
            .name("isNull")
            .expectedValue("(null)")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject exactly matches the supplied type.
     * <p/>
     * Note: If the subject is a subtype of the supplied type, this check will fail.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}
     *
     * @return This expression (fluent interface).
     */
    public B isType(final Class<?> type) {

        EvaluationBuilder builder = newEvaluation(o -> o.getClass().equals(type));

        if (type == null) {
            builder.name("isType[(null)]")
                .expectedValue(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        } else {
            builder.name("isType[" + type.getName() + "]")
                .expectedValue(type.toString())
                .hint("Subject should be exactly of type " + type);
        }

        return builder.actualValue(o -> o.getClass().toString())
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is not an exact match of the supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public B isNotType(final Class<?> type) {

        EvaluationBuilder builder = newEvaluation(o -> type != null && !o.getClass().equals(type));

        if (type == null) {
            builder.name("isNotType[(null)]")
                .expectedValue(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        } else {
            builder.name("isNotType[" + type.getName() + "]")
                .expectedValue("not[" + type.toString() + "]")
                .hint("Subject should be any type other than " + type);
        }

        return builder.actualValue(o -> o.getClass().toString())
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject matches or is a subtype of the supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code type} is the supplied type and
     *     {@code subject} is the subject of the expression:
     *     <pre><code>
     *         type.isAssignableFrom(subject.getClass())
     *     </code></pre>
     * @see Class#isAssignableFrom(Class)
     */
    public B isInstanceOf(final Class<?> type) {

        EvaluationBuilder builder = newEvaluation(o -> type != null && type.isAssignableFrom(o.getClass()));

        if (type == null) {
            builder.name("isInstanceOf[(null)]")
                .expectedValue(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        } else {
            builder.name("isInstanceOf[" + type.getName() + "]")
                .expectedValue("instanceof[" + type + "]")
                .hint("Subject should be exactly of type " + type + " or a subtype of type " + type);
        }

        return builder.actualValue(o -> o.getClass().toString())
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject does not exactly match or is not a subtype of the
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
     *         !type.isAssignableFrom(subject.getClass())
     *     </code></pre>
     * @see Class#isAssignableFrom(Class)
     */
    public B isNotInstanceOf(final Class<?> type) {

        EvaluationBuilder builder = newEvaluation(o -> type != null && !type.isAssignableFrom(o.getClass()));

        if (type == null) {
            builder.name("isNotInstanceOf[(null)]")
                .expectedValue(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        } else {
            builder.name("isNotInstanceOf[" + type.getName() + "]")
                .expectedValue("not[instanceof[" + type + "]]")
                .hint("Subject should be any type other than " + type + " and not a subtype of type " + type);
        }

        return builder.actualValue(o -> o.getClass().toString())
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is the same as the supplied object.
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
     *         subject == object
     *     </code></pre>
     */
    public B is(final Object other) {
        return newNullableEvaluation(o -> o == other)
            .name("is[" + stringOf(other) + "]")
            .expectedValue(o -> stringOf(other))
            .hint("Subject should be identical to " + other + " (using ==)")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is not the same as the supplied object.
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
     *         subject != object
     *     </code></pre>
     */
    public B isNot(final Object other) {
        return newNullableEvaluation(o -> o != other)
            .name("isNot[" + stringOf(other) + "]")
            .expectedValue("not[" + stringOf(other) + "]")
            .hint("Subject should not be identical to " + other + " (using !=)")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is equal to the supplied object.
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
     *         Objects.equals(subject, other);
     *     </code></pre>
     * @see Objects#equals(Object, Object)
     */
    public B isEqualTo(final Object other) {
        return newNullableEvaluation(o -> Objects.equals(o, other))
            .name("isEqualTo[" + stringOf(other) + "]")
            .expectedValue(stringOf(other))
            .hint("Subject should be equal to " + other + " (using subject.equals(" + other + "))")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the subject is not equal to the supplied object.
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
     *         !Objects.equals(subject, other);
     *     </code></pre>
     * @see Objects#equals(Object, Object)
     */
    public B isNotEqualTo(final Object other) {
        return newNullableEvaluation(o -> !Objects.equals(o, other))
            .name("isNotEqualTo[" + stringOf(other) + "]")
            .expectedValue("not[" + stringOf(other) + "]")
            .hint("Subject should be equal to " + other + " (using !subject.equals(" + other + "))")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the hash code of the subject is equal to the supplied
     * hash code.
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code
     * and {@code subject} is the subject of the expression:
     *     <pre><code>
     *         subject.hashCode() == hashCode
     *     </code></pre>
     */
    public B hashCodeIs(final int hashCode) {
        return newEvaluation(o -> o.hashCode() == hashCode)
            .name("hashCode == " + hashCode)
            .expectedValue(hashCode)
            .actualIntValue(Object::hashCode)
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the hash code of the subject is not equal to the supplied
     * hash code.
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code
     * and {@code subject} is the subject of the expression:
     *     <pre><code>
     *         subject.hashCode() != hashCode
     *     </code></pre>
     */
    public B hashCodeIsNot(final int hashCode) {
        return newEvaluation(o -> o.hashCode() != hashCode)
            .name("hashCode != " + hashCode)
            .expectedValue("not[" + hashCode + "]")
            .actualIntValue(Object::hashCode)
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the {@code toString()} value of the subject is equal to
     * the supplied value.
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code expected} is the supplied string
     * and {@code subject} is the subject of the expression:
     *     <pre><code>
     *         subject.toString().equals(expected)
     *     </code></pre>
     */
    public B toStringIs(final String expected) {
        return newEvaluation(o -> o.toString().equals(expected))
            .name("toString().equals(" + expected + ")")
            .expectedValue(expected)
            .hint("Subject's toString() value should equal " + expected + " (using subject.toString().equals(" + expected + "))")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the {@code toString()} value of the subject is not equal to
     * the supplied value.
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     *
     * @implNote This check is equivalent to the following statement, where {@code expected} is the supplied string
     * and {@code subject} is the subject of the expression:
     *     <pre><code>
     *         !subject.toString().equals(expected)
     *     </code></pre>
     */
    public B toStringIsNot(final String expected) {
        return newEvaluation(o -> !o.toString().equals(expected))
            .name("not[toString().equals(" + expected + ")]")
            .expectedValue("not[" + expected + "]")
            .hint("Subject's toString() value should not equal " + expected + " (using !subject.toString().equals(" + expected + "))")
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the supplied predicate is true.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public B satisfies(final Predicate<T> predicate) {
        return newEvaluation(predicate)
            .name("predicate")
            .expectedValue(PREDICATE_SATISFIED)
            .actualValue(o -> predicate.test(o) ? PREDICATE_SATISFIED : PREDICATE_UNSATISFIED)
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the supplied condition is true.
     *
     * @param condition
     *     The condition to test.
     *
     * @return This expression (fluent interface).
     */
    public B satisfies(final Condition<T> condition) {
        return newEvaluation(condition)
            .name("condition: " + conditionName(condition))
            .expectedValue(CONDITION_SATISFIED)
            .actualValue(o -> condition.test(o) ? CONDITION_SATISFIED : CONDITION_UNSATISFIED)
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the supplied predicate is false.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public B doesNotSatisfy(final Predicate<T> predicate) {
        return newEvaluation(o -> !predicate.test(o))
            .name("not[predicate]")
            .expectedValue(PREDICATE_UNSATISFIED)
            .actualValue(o -> predicate.test(o) ? PREDICATE_SATISFIED : PREDICATE_UNSATISFIED)
            .append();
    }

    /**
     * Adds an evaluation to the expression that checks if the supplied condition is false.
     *
     * @param condition
     *     The condition to test.
     *
     * @return This expression (fluent interface).
     */
    public B doesNotSatisfy(final Condition<T> condition) {
        return newEvaluation(o -> !condition.test(o))
            .name("not[condition: " + conditionName(condition) + "]")
            .expectedValue(CONDITION_UNSATISFIED)
            .actualValue(o -> condition.test(o) ? CONDITION_SATISFIED : CONDITION_UNSATISFIED)
            .append();
    }

    private static String conditionName(final Condition<?> condition) {
        return condition.name() != null && !condition.name().trim().isEmpty() ? condition.name() : "<unnamed>";
    }
}
