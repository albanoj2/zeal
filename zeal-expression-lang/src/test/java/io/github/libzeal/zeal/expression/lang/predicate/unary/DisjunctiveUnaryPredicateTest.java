package io.github.libzeal.zeal.expression.lang.predicate.unary;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleRationale;
import io.github.libzeal.zeal.expression.lang.predicate.PredicateAssertions;
import io.github.libzeal.zeal.expression.lang.predicate.Predicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisjunctiveUnaryPredicateTest {

    private String name;
    private DisjunctiveUnaryPredicate<Object> predicate;

    @BeforeEach
    void setUp() {

        name = "foo";

        predicate = new DisjunctiveUnaryPredicate<>(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<UnaryPredicate<Object>> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveUnaryPredicate<>(null, list)
        );
    }

    @Test
    void givenNullPredicateList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveUnaryPredicate<>(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveUnaryPredicate<>(null)
        );
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, predicate.name());
    }

    @Test
    void givenNoSubPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryPredicate<Object> subPredicate = Predicates.unaryPredicate(Result.PASSED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryPredicate<Object> subPredicate = Predicates.unaryPredicate(Result.FAILED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryPredicate<Object> subPredicate = Predicates.unaryPredicate(Result.SKIPPED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingPredicatePrependedBeforePassingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryPredicate<Object> passingSubPredicate = Predicates.unaryPredicate(Result.PASSED);
        UnaryPredicate<Object> failingSubPredicate = Predicates.unaryPredicate(Result.FAILED);

        predicate.append(passingSubPredicate);
        predicate.prepend(failingSubPredicate);

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsNotSkipped(failingSubPredicate, subject);
    }

    private static void assertIsSkipped(UnaryPredicate<Object> predicate, Object subject) {
        verify(predicate, times(1)).skip();
        verify(predicate, never()).evaluate(subject);
    }

    private static void assertIsNotSkipped(UnaryPredicate<Object> predicate, Object subject) {
        verify(predicate, never()).skip();
        verify(predicate, times(1)).evaluate(subject);
    }

    @Test
    void givenOnePassingPredicatePrependedBeforeFailingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryPredicate<Object> passingSubPredicate = Predicates.unaryPredicate(Result.PASSED);
        UnaryPredicate<Object> failingSubPredicate = Predicates.unaryPredicate(Result.FAILED);

        predicate.append(failingSubPredicate);
        predicate.prepend(passingSubPredicate);

        Evaluation evaluation = predicate.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsSkipped(failingSubPredicate, subject);
    }

    @Test
    void givenNoChildren_whenSkip_thenEvaluationIsCorrect() {

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertTrue(skippedEvaluation.children().isEmpty());
    }

    @Test
    void givenOneChild_whenSkip_thenEvaluationIsCorrect() {

        final String subPredicateName = "predicate 1";
        UnaryPredicate<Object> subPredicate = Predicates.unaryPredicate(subPredicateName, Result.PASSED);

        predicate.append(subPredicate);

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(1, skippedEvaluation.children().size());
        PredicateAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName);
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subPredicateName1 = "predicate 1";
        final String subPredicateName2 = "predicate 2";
        UnaryPredicate<Object> subPredicate1 = Predicates.unaryPredicate(subPredicateName1, Result.FAILED);
        UnaryPredicate<Object> subPredicate2 = Predicates.unaryPredicate(subPredicateName2, Result.FAILED);

        predicate.append(subPredicate1);
        predicate.append(subPredicate2);

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(2, skippedEvaluation.children().size());
        PredicateAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName1);
        PredicateAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName2);
    }
}
