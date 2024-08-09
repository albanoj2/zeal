package io.github.libzeal.zeal.assertion.api;

import io.github.libzeal.zeal.assertion.Assertions;
import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AssertionsTest {

    @Test
    void givenNullExpression_whenRequire_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(null)
        );
    }

    @Test
    void givenNullEvaluation_whenRequire_thenExceptionThrown() {

        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(null, subject);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression)
        );
    }

    @SuppressWarnings("unchecked")
    private static UnaryExpression<Object> expression(final Evaluation evaluation, Object subject) {

        final UnaryExpression<Object> expression = mock(UnaryExpression.class);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(subject).when(expression).subject();

        return expression;
    }

    @Test
    void givenFailingEvaluationWithNullSubject_whenRequire_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, null);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression)
        );
    }

    private static Evaluation evaluation(final Result failed) {

        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(failed).when(evaluation).result();

        return evaluation;
    }

    @Test
    void givenFailingEvaluationWithNonNullSubject_whenRequire_thenExceptionThrown() {

        final Object subject = new Object();
        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            PreconditionFailedException.class,
            () -> Assertions.require(expression)
        );
    }

    @Test
    void givenPassedEvaluationWithNullSubject_whenRequire_thenSubjectReturned() {

        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, null);

        Object returnedSubject = Assertions.require(expression);

        assertNull(returnedSubject);
    }

    @Test
    void givenPassedEvaluationWithNonNullSubject_whenRequire_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNullSubject_whenRequire_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, null);

        Object returnedSubject = Assertions.require(expression);

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNonNullSubject_whenRequire_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenNullExpression_whenRequireWithMessage_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(null, "some message")
        );
    }

    @Test
    void givenNullEvaluation_whenRequireWithMessage_thenExceptionThrown() {

        final Object subject = new Object();
        final Evaluation evaluation = null;
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression, "some message")
        );
    }

    @Test
    void givenFailingEvaluationWithNullSubject_whenRequireWithMessage_thenExceptionThrown() {

        final Object subject = null;
        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression, "some message")
        );
    }

    @Test
    void givenFailingEvaluationWithNonNullSubject_whenRequireWithMessage_thenExceptionThrown() {

        final Object subject = new Object();
        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            PreconditionFailedException.class,
            () -> Assertions.require(expression, "some message")
        );
    }

    @Test
    void givenPassedEvaluationWithNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Object subject = null;
        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, "some message");

        assertNull(returnedSubject);
    }

    @Test
    void givenPassedEvaluationWithNonNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, "some message");

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Object subject = null;
        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, "some message");

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNonNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, "some message");

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenNullExpression_whenRequireWithNullMessage_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(null, null)
        );
    }

    @Test
    void givenNullEvaluation_whenRequireWithNullMessage_thenExceptionThrown() {

        final Object subject = new Object();
        final Evaluation evaluation = null;
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression, null)
        );
    }

    @Test
    void givenFailingEvaluationWithNullSubject_whenRequireWithNullMessage_thenExceptionThrown() {

        final Object subject = null;
        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            NullPointerException.class,
            () -> Assertions.require(expression, null)
        );
    }

    @Test
    void givenFailingEvaluationWithNonNullSubject_whenRequireWithNullMessage_thenExceptionThrown() {

        final Object subject = new Object();
        final Result result = Result.FAILED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        assertThrows(
            PreconditionFailedException.class,
            () -> Assertions.require(expression, null)
        );
    }

    @Test
    void givenPassedEvaluationWithNullSubject_whenRequireWithNullMessage_thenSubjectReturned() {

        final Object subject = null;
        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, null);

        assertNull(returnedSubject);
    }

    @Test
    void givenPassedEvaluationWithNonNullSubject_whenRequireWithNullMessage_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.PASSED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, null);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNullSubject_whenRequireWithNullMessage_thenSubjectReturned() {

        final Object subject = null;
        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, null);

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationWithNonNullSubject_whenRequireWithNullMessage_thenSubjectReturned() {

        final Object subject = new Object();
        final Result result = Result.SKIPPED;
        final Evaluation evaluation = evaluation(result);
        final UnaryExpression<Object> expression = expression(evaluation, subject);

        Object returnedSubject = Assertions.require(expression, null);

        assertEquals(subject, returnedSubject);
    }
}
