package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.lang.condition.SimpleCondition;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.types.core.unary.test.EvaluatedExpressionAssertion;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A base class designed to be extended by test fixtures used to test custom expressions.
 * <p />
 * For example:
 * <pre><code>
 * public class StringExpressionCheck extends ObjectExpressionTest<String, StringExpression> {
 *
 *    {@literal @}Override
 *     protected void extendTestCases(ExpressionTestCaseBuilder<T, E> builder) {
 *         builder.newTest((expression, value) -> expression.isEmpty())
 *             .value("foo")
 *             .expectedState(FAILED)
 *             .expectedName("isEmpty")
 *             .expectedExpectedValue("true")
 *             .expectedActualValue("false")
 *             .addTest();
 *     }
 * }
 * </code></pre>
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the expression under test. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>.
 *     This type allows for the fluent interface methods of this class to return the subtype object, rather than
 *     {@link ObjectUnaryExpression}. For example:
 *     <pre><code>
 *         public class StringExpressionCheck extends ObjectExpressionTest<String, StringExpression> { //...
 *     </code></pre>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ObjectUnaryExpressionTest<T, E extends ObjectUnaryExpression<T, E>> {

    private final SimpleCondition<T> alwaysTrueCondition = new SimpleCondition<>("Always true", s -> true);
    private final SimpleCondition<T> alwaysFalseCondition = new SimpleCondition<>("Always false", s -> false);

    protected abstract E expression(T value);

    @Test
    final void givenNoEvaluations_whenConstruct_thenCorrectEvaluation() {

        T value = exampleValue1();

        E expression = expression(value);
        EvaluatedExpressionAssertion<?> assertion = new EvaluatedExpressionAssertion<>(expression.evaluate());

        assertEquals(value, expression.subject());
        assertion.assertStateIs(PASSED);
        assertion.assertCompoundExpectedValue();
        assertion.assertCompoundActualValueIs(0, 0, 0);
    }

    @Test
    final void givenNotNullFollowsAnotherEvaluation_whenEvaluate_thenNotNullPreemptsOtherEvaluation() {

        E expression = expression(null);

        expression.isNull().isNotNull();

        Evaluation eval = expression.evaluate();
        Evaluation first = eval.children().get(0);
        Evaluation second = eval.children().get(1);

        EvaluatedExpressionAssertion<?> firstAssertion = new EvaluatedExpressionAssertion<>(first);
        EvaluatedExpressionAssertion<?> secondAssertion = new EvaluatedExpressionAssertion<>(second);

        assertNotNull(first);
        assertNotNull(second);
        firstAssertion.assertStateIs(FAILED);
        firstAssertion.assertNameIs("isNotNull");
        secondAssertion.assertStateIs(SKIPPED);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("testCaseStream")
    final void whenEvaluate_thenCorrectEvaluation(
        String testCaseName,
        BiFunction<E, T, E> modifier,
        T value,
        Result expectedState,
        Function<T, String> expectedName,
        BiFunction<E, T, String> expectedExpectedValue,
        BiFunction<E, T, String> expectedActualValue,
        BiFunction<E, T, String> expectedHint
    ) {

        E expression = expression(value);

        E returnedExpression = modifier.apply(expression, value);

        assertNotNull(returnedExpression);
        assertEquals(expression.getClass(), returnedExpression.getClass());

        Evaluation eval = expression.evaluate();
        Evaluation first = eval.children().get(0);
        EvaluatedExpressionAssertion<?> firstEvaluation = new EvaluatedExpressionAssertion<>(first);

        assertNotNull(first);
        firstEvaluation.assertStateIs(expectedState);
        firstEvaluation.assertNameIs(expectedName.apply(value));
        firstEvaluation.assertExpectedIs(expectedExpectedValue.apply(expression, value));
        firstEvaluation.assertActualIs(expectedActualValue.apply(expression, value));

        if (expectedHint != null) {
            firstEvaluation.assertHintIs(expectedHint.apply(expression, value));
        }
    }

    private Stream<Arguments> testCaseStream() {

        ExpressionTestCaseBuilder<T, E> builder = ExpressionTestCaseBuilder.newBuilder();

        isNullTestCases(builder);
        isNotNullTestCases(builder);
        isTypeTestCases(builder);
        isNotTypeTestCases(builder);
        isInstanceOfTestCases(builder);
        isNotInstanceOfTestCases(builder);
        isTestCases(builder);
        isNotTestCases(builder);
        isEqualToTestCases(builder);
        isNotEqualToTestCases(builder);
        hashCodeIsTestCases(builder);
        hashCodeIsNotTestCases(builder);
        toStringIsTestCases(builder);
        toStringIsNotTestCases(builder);
        satisfiesPredicateTestCases(builder);
        doesNotSatisfyPredicateTestCases(builder);
        satisfiesConditionTestCases(builder);
        doesNotSatisfyConditionTestCases(builder);

        extendTestCases(builder);

        return builder.stream();
    }

    private void isNullTestCases(ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNull())
                .value(null)
                .expectedState(PASSED)
                .expectedName("isNull")
                .expectedExpected("(null)")
                .expectedActual("(null)")
                .addTest()
            .newTest((expression, value) -> expression.isNull())
                .value(exampleValue1())
                .expectedState(FAILED)
                .expectedName("isNull")
                .expectedExpected("(null)")
                .expectedActual(exampleValue1().toString())
                .addTest();
    }

    private void isNotNullTestCases(ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNotNull())
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNotNull")
                .expectedExpected("not[(null)]")
                .expectedActual("(null)")
                .addTest()
            .newTest((expression, value) -> expression.isNotNull())
                .value(exampleValue1())
                .expectedState(PASSED)
                .expectedName("isNotNull")
                .expectedExpected("not[(null)]")
                .expectedActual(exampleValue1().toString())
                .addTest();
    }

    private void isTypeTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T nonNullValue = exampleValue1();

        builder.newTest((expression, value) -> expression.isType(nonNullValue.getClass()))
                .value(nonNullValue)
                .expectedState(PASSED)
                .expectedName("isType[" + nonNullValue.getClass() + "]")
                .expectedExpected(nonNullValue.getClass().toString())
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isType(CanonicalClass.class))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isType[" + CanonicalClass.class + "]")
                .expectedExpected(CanonicalClass.class.toString())
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isType(null))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isType[(null)]")
                .expectedExpected("Always fail: cannot compare to (null) type")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest();
    }

    private void isNotTypeTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T nonNullValue = exampleValue1();

        builder.newTest((expression, value) -> expression.isNotType(nonNullValue.getClass()))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isNotType[" + nonNullValue.getClass() + "]")
                .expectedExpected("not[" + nonNullValue.getClass() + "]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotType(CanonicalClass.class))
                .value(nonNullValue)
                .expectedState(PASSED)
                .expectedName("isNotType[" + CanonicalClass.class + "]")
                .expectedExpected("not[" + CanonicalClass.class + "]")
                .expectedActual((expression, value) -> value.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotType(null))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isNotType[(null)]")
                .expectedExpected("Always fail: cannot compare to (null) type")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest();
    }

    private void isInstanceOfTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T nonNullValue = exampleValue1();

        builder.newTest((expression, value) -> expression.isInstanceOf(nonNullValue.getClass()))
                .value(nonNullValue)
                .expectedState(PASSED)
                .expectedName("isInstanceOf[" + nonNullValue.getClass() + "]")
                .expectedExpected("instanceof[" + nonNullValue.getClass() + "]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(Object.class))
                .value(nonNullValue)
                .expectedState(PASSED)
                .expectedName("isInstanceOf[" + Object.class + "]")
                .expectedExpected("instanceof[" + Object.class + "]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(CanonicalClass.class))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isInstanceOf[" + CanonicalClass.class + "]")
                .expectedExpected("instanceof[" + CanonicalClass.class + "]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(null))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isInstanceOf[(null)]")
                .expectedExpected("Always fail: cannot compare to (null) type")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest();
    }

    private void isNotInstanceOfTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T nonNullValue = exampleValue1();

        builder.newTest((expression, value) -> expression.isNotInstanceOf(nonNullValue.getClass()))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[" + nonNullValue.getClass() + "]")
                .expectedExpected("not[instanceof[" + nonNullValue.getClass() +"]]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(Object.class))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[" + Object.class + "]")
                .expectedExpected("not[instanceof[" + Object.class + "]]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(CanonicalClass.class))
                .value(nonNullValue)
                .expectedState(PASSED)
                .expectedName("isNotInstanceOf[" + CanonicalClass.class + "]")
                .expectedExpected("not[instanceof["  + CanonicalClass.class +  "]]")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(null))
                .value(nonNullValue)
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[(null)]")
                .expectedExpected("Always fail: cannot compare to (null) type")
                .expectedActual(nonNullValue.getClass().toString())
                .addTest();
    }

    private void isTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.is(value1))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("is[" + value1 + "]")
                .expectedExpected((expression, value) -> value.toString())
                .expectedActual(value1.toString())
                .addTest()
            .newTest((expression, value) -> expression.is(value1))
                .value(value2)
                .expectedState(FAILED)
                .expectedName("is[" + value1 + "]")
                .expectedExpected(value1.toString())
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.is(null))
                .value(null)
                .expectedState(PASSED)
                .expectedName("is[(null)]")
                .expectedExpected("(null)")
                .expectedActual("(null)")
                .addTest();
    }

    private void isNotTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isNot(value1))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("isNot[" + value1 + "]")
                .expectedExpected("not[" + value1.toString() + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNot(value1))
                .value(value2)
                .expectedState(PASSED)
                .expectedName("isNot[" + value1 + "]")
                .expectedExpected("not[" + value1 + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNot(null))
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNot[(null)]")
                .expectedExpected("not[(null)]")
                .expectedActual("(null)")
                .addTest();
    }

    private void isEqualToTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest(ObjectUnaryExpression::isEqualTo)
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "isEqualTo[" + value1 + "]")
                .expectedExpected((expression, value) -> value.toString())
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(value1))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "isEqualTo[" + value1 + "]")
                .expectedExpected((expression, value) -> value1.toString())
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(null))
                .value(null)
                .expectedState(PASSED)
                .expectedName("isEqualTo[(null)]")
                .expectedExpected("(null)")
                .expectedActual("(null)")
                .addTest();
    }

    private void isNotEqualToTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest(ObjectUnaryExpression::isNotEqualTo)
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "isNotEqualTo[" + value + "]")
                .expectedExpected((expression, value) -> "not[" + value.toString() + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(value1))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "isNotEqualTo[" + value1 + "]")
                .expectedExpected((expression, value) -> "not[" + value1.toString() + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(null))
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNotEqualTo[(null)]")
                .expectedExpected("not[(null)]")
                .expectedActual("(null)")
                .addTest();
    }

    private void hashCodeIsTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.hashCodeIs(value1.hashCode()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "hashCode == " + value.hashCode())
                .expectedExpected((expression, value) -> String.valueOf(value1.hashCode()))
                .expectedActual((expression, value) -> String.valueOf(value.hashCode()))
                .addTest()
            .newTest((expression, value) -> expression.hashCodeIs(value1.hashCode()))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "hashCode == " + value1.hashCode())
                .expectedExpected((expression, value) -> String.valueOf(value1.hashCode()))
                .expectedActual((expression, value) -> String.valueOf(value.hashCode()))
                .addTest();
    }

    private void hashCodeIsNotTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.hashCodeIsNot(value1.hashCode()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "hashCode != " + value.hashCode())
                .expectedExpected((expression, value) -> "not[" + value.hashCode() + "]")
                .expectedActual((expression, value) -> String.valueOf(value.hashCode()))
                .addTest()
            .newTest((expression, value) -> expression.hashCodeIsNot(value1.hashCode()))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "hashCode != " + value1.hashCode())
                .expectedExpected((expression, value) -> "not[" + value1.hashCode() + "]")
                .expectedActual((expression, value) -> String.valueOf(value.hashCode()))
                .addTest();
    }

    private void toStringIsTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.toStringIs(value1.toString()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "toString().equals(" + value1 + ")")
                .expectedExpected((expression, value) -> value1.toString())
                .expectedActual((expression, value) -> value.toString())
            .addTest()
                .newTest((expression, value) -> expression.toStringIs(value1.toString()))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "toString().equals(" + value1 + ")")
                .expectedExpected((expression, value) -> value1.toString())
                .expectedActual((expression, value) -> value.toString())
                .addTest();
    }

    private void toStringIsNotTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.toStringIsNot(value1.toString()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "not[toString().equals(" + value1 + ")]")
                .expectedExpected((expression, value) -> "not[" + value1 + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.toStringIsNot(value1.toString()))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "not[toString().equals(" + value1 + ")]")
                .expectedExpected((expression, value) -> "not[" + value1 + "]")
                .expectedActual((expression, value) -> value.toString())
                .addTest();
    }

    private void satisfiesPredicateTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();

        builder.newTest((expression, value) -> expression.satisfies(o -> true))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("predicate")
                .expectedExpected("Predicate satisfied")
                .expectedActual("Predicate satisfied")
                .addTest()
            .newTest((expression, value) -> expression.satisfies(o -> false))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("predicate")
                .expectedExpected("Predicate satisfied")
                .expectedActual("Predicate unsatisfied")
                .addTest();
    }

    private void doesNotSatisfyPredicateTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();

        builder.newTest((expression, value) -> expression.doesNotSatisfy(o -> true))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("not[predicate]")
                .expectedExpected("Predicate unsatisfied")
                .expectedActual("Predicate satisfied")
                .addTest()
            .newTest((expression, value) -> expression.doesNotSatisfy(o -> false))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("not[predicate]")
                .expectedExpected("Predicate unsatisfied")
                .expectedActual("Predicate unsatisfied")
                .addTest();
    }

    private void satisfiesConditionTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();

        builder.newTest((expression, value) -> expression.satisfies(alwaysTrueCondition))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("condition: " + alwaysTrueCondition.name())
                .expectedExpected("Condition satisfied")
                .expectedActual("Condition satisfied")
                .addTest()
            .newTest((expression, value) -> expression.satisfies(alwaysFalseCondition))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("condition: " + alwaysFalseCondition.name())
                .expectedExpected("Condition satisfied")
                .expectedActual("Condition unsatisfied")
                .addTest();
    }

    private void doesNotSatisfyConditionTestCases(ExpressionTestCaseBuilder<T, E> builder) {

        T value1 = exampleValue1();

        builder.newTest((expression, value) -> expression.doesNotSatisfy(alwaysTrueCondition))
                .value(value1)
                .expectedState(FAILED)
                .expectedName("not[condition: " + alwaysTrueCondition.name() + "]")
                .expectedExpected("Condition unsatisfied")
                .expectedActual("Condition satisfied")
                .addTest()
            .newTest((expression, value) -> expression.doesNotSatisfy(alwaysFalseCondition))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("not[condition: " + alwaysFalseCondition.name() + "]")
                .expectedExpected("Condition unsatisfied")
                .expectedActual("Condition unsatisfied")
                .addTest();
    }

    /**
     * An extension point that allows for custom test cases to be added and executed using the supplied builder.
     *
     * @param builder
     *     THe builder used to create new test cases.
     */
    protected void extendTestCases(ExpressionTestCaseBuilder<T, E> builder) {}

    /**
     * Obtains an example value. This value must be different from the value returned by {@link #exampleValue2()}. The
     * following properties must hold true for the two methods:
     * <pre><code>
     *     exampleValue1() != null
     *     exampleValue2() != null
     *     exampleValue1().equals(exampleValue2() == false
     *     exampleValue1().hashCode() != exampleValue2().hashCode()
     *     exampleValue1().toString().equals(exampleValue2().toString() == false
     *     exampleValue1().toString().equalsIgnoreCase(exampleValue2().toString() == false
     * </code></pre>
     *
     * @return An example value.
     */
    protected abstract T exampleValue1();

    /**
     * Obtains an example value. This value must be different from the value returned by {@link #exampleValue1()}. The
     * following properties must hold true for the two methods:
     * <pre><code>
     *     exampleValue1() != null
     *     exampleValue2() != null
     *     exampleValue1().equals(exampleValue2() == false
     *     exampleValue1().hashCode() != exampleValue2().hashCode()
     *     exampleValue1().toString().equals(exampleValue2().toString() == false
     *     exampleValue1().toString().equalsIgnoreCase(exampleValue2().toString() == false
     * </code></pre>
     *
     * @return An example value.
     */
    protected abstract T exampleValue2();

    private static class CanonicalClass {}
}
