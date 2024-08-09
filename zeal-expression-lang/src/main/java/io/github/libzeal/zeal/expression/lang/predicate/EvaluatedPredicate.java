package io.github.libzeal.zeal.expression.lang.predicate;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.predicate.unary.UnaryPredicate;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An {@link Evaluation} that resulted from an operation (such as {@link UnaryPredicate}).
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class EvaluatedPredicate implements Evaluation {

    private final String name;
    private final Result result;
    private final Rationale rationale;
    private final List<Evaluation> children;

    /**
     * Creates a skipped evaluation without any children.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param rationale
     *     The rationale for the evaluation results.
     *
     * @return A skipped evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static EvaluatedPredicate skipped(final String name, final Rationale rationale) {
        return EvaluatedPredicate.skipped(name, rationale, Collections.emptyList());
    }

    /**
     * Creates a skipped evaluation.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param rationale
     *     The rationale for the evaluation results.
     * @param children
     *     The children of the evaluation.
     *
     * @return A skipped evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static EvaluatedPredicate skipped(final String name, final Rationale rationale,
                                             List<Evaluation> children) {
        return new EvaluatedPredicate(name, Result.SKIPPED, rationale, children);
    }

    /**
     * Creates a new evaluation without any children.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param result
     *     The result of the evaluation.
     * @param rationale
     *     The rationale for the evaluation results.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public EvaluatedPredicate(final String name, final Result result, final Rationale rationale) {
        this(name, result, rationale, Collections.emptyList());
    }

    /**
     * Creates a new evaluation.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param result
     *     The result of the evaluation.
     * @param rationale
     *     The rationale for the evaluation results.
     * @param children
     *     The children of the evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public EvaluatedPredicate(final String name, final Result result, final Rationale rationale,
                              final List<Evaluation> children) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.children = requireNonNull(children);
    }

    @Override
    public Result result() {
        return result;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public List<Evaluation> children() {
        return children;
    }
}
