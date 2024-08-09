package io.github.libzeal.zeal.expression.lang.predicate.unary;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleRationale;
import io.github.libzeal.zeal.expression.lang.predicate.EvaluatedPredicate;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A compound predicate where all sub-predicate must be pass for this compound predicate to pass.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class ConjunctiveUnaryPredicate<T> implements CompoundUnaryPredicate<T> {

    private final String name;
    private final List<UnaryPredicate<T>> children;

    /**
     * Creates a new conjunctive criteria.
     *
     * @param name
     *     The name of the predicate.
     * @param children
     *     The predicates used initialize the compound predicate.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public ConjunctiveUnaryPredicate(String name, List<UnaryPredicate<T>> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new conjunctive predicate with an empty default sub-predicate list.
     *
     * @param name
     *     The name of the predicate.
     *
     * @implNote The default sub-predicate list has an initial capacity of 1.
     */
    public ConjunctiveUnaryPredicate(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void append(UnaryPredicate<T> predicate) {
        this.children.add(predicate);
    }

    @Override
    public void prepend(UnaryPredicate<T> predicate) {
        this.children.add(0, predicate);
    }

    @Override
    public Evaluation skip() {
        return new SkippedCompoundUnaryPredicateEvaluation<>(name, children);
    }

    @Override
    public Evaluation evaluate(final T subject) {

        final int total = children.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (UnaryPredicate<T> predicate : children) {

            if (failed > 0) {
                evaluated.add(predicate.skip());
            }
            else {
                final Evaluation evaluation = predicate.evaluate(subject);
                final Result result = evaluation.result();

                switch (result) {
                    case PASSED:
                        passed++;
                        break;
                    case FAILED:
                        failed++;
                        break;
                    case SKIPPED:
                        skipped++;
                        break;
                }

                evaluated.add(evaluation);
            }
        }

        final Result compountResult = computeResult(passed, failed, total);
        final Rationale rationale = computeRationale(passed, failed, skipped);

        return new EvaluatedPredicate(name, compountResult, rationale, evaluated);
    }

    private Result computeResult(final int passed, final int failed, final int total) {

        if (total == 0) {
            return Result.PASSED;
        }
        else if (failed > 0) {
            return Result.FAILED;
        }
        else if (passed == total) {
            return Result.PASSED;
        }
        else {
            return Result.SKIPPED;
        }
    }

    private Rationale computeRationale(final int passed, final int failed, final int skipped) {

        final String expected = "All children must pass";
        final String actual =
            "Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;

        return new SimpleRationale(expected, actual);
    }
}
