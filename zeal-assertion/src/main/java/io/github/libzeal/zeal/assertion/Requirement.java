package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.EvaluationFormatter;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

import static java.util.Objects.requireNonNull;

public class Requirement {

    private final ExceptionSupplier onNullExceptionSupplier;
    private final ExceptionSupplier onFailExceptionSupplier;
    private final EvaluationFormatter formatter;

    private Requirement(final ExceptionSupplier onNullExceptionSupplier,
                  final ExceptionSupplier onFailExceptionSupplier, final EvaluationFormatter formatter) {
        this.onNullExceptionSupplier = requireNonNull(onNullExceptionSupplier);
        this.onFailExceptionSupplier = requireNonNull(onFailExceptionSupplier);
        this.formatter = requireNonNull(formatter);
    }

    private Requirement() {
        this(NullPointerException::new, PreconditionFailedException::new,
            new SimpleEvaluationFormatter());
    }

    public static Requirement create() {
        return new Requirement();
    }

    public Requirement thatThrowsOnNull(ExceptionSupplier supplier) {
        return new Requirement(supplier, this.onFailExceptionSupplier, this.formatter);
    }

    public Requirement thatThrowsOnFail(ExceptionSupplier supplier) {
        return new Requirement(this.onNullExceptionSupplier, supplier, this.formatter);
    }

    public Requirement withFormatter(EvaluationFormatter formatter) {
        return new Requirement(this.onNullExceptionSupplier, this.onFailExceptionSupplier, formatter);
    }

    public Requirement thatThrowsIllegalArgumentExceptionOnNull() {
        return thatThrowsOnNull(IllegalArgumentException::new);
    }

    public <T> T require(final UnaryExpression<T> expression) {
        return require(expression, "Precondition failed");
    }

    public <T> T require(final UnaryExpression<T> expression, final String message) {

        if (expression == null) {
            throw new NullPointerException("Cannot evaluate null expression");
        }

        final Evaluation evaluation = expression.evaluate();

        if (evaluation == null) {
            throw new NullPointerException("Cannot process null evaluation");
        }
        else if (evaluation.result() == null) {
            throw new NullPointerException("Cannot process evaluation with null result");
        }

        final T subject = expression.subject();

        if (isFailed(evaluation)) {

            String formattedMessage = formatMessage(message, evaluation);

            if (subject == null) {
                throw new NullPointerException(formattedMessage);
            }
            else {
                throw new PreconditionFailedException(formattedMessage);
            }
        }
        else {
            return subject;
        }
    }

    private String formatMessage(final String message, final Evaluation evaluation) {
        return message + "\n\n" + formatter.format(evaluation) + "\nStack trace:";
    }

    private static boolean isFailed(final Evaluation eval) {
        return eval.result().equals(Result.FAILED);
    }
}
