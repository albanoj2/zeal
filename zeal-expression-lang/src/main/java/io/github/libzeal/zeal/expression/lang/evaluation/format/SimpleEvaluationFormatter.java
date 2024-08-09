package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;

import java.util.Optional;

/**
 * A simple formatter that formats the root cause of a failed evaluation and provides a nested visualization of all
 * compound evaluated expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class SimpleEvaluationFormatter implements EvaluationFormatter {

    static final String INDENT = "       ";

    @Override
    public String format(Evaluation eval) {

        final StringBuilder builder = new StringBuilder();
        final RootCauseFinder finder = new RootCauseFinder(eval);

        finder.find().ifPresent(builder::append);

        return builder.append(format(eval, 0))
            .toString();
    }

    private static String format(Evaluation eval, int nestedLevel) {

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(nestedLevel))
            .append("[")
            .append(format(eval.result()))
            .append("] ")
            .append(eval.name());

        if (eval.children().isEmpty() && eval.result().equals(Result.FAILED)) {
            builder.append("\n")
                .append(format(eval.rationale(), nestedLevel + 1));
        }

        for (Evaluation child : eval.children()) {
            builder.append("\n")
                .append(format(child, nestedLevel + 1));
        }

        return builder.toString();
    }

    private static String indent(int level) {

        final StringBuilder intent = new StringBuilder();

        for (int i = 0; i < level; i++) {
            intent.append(INDENT);
        }

        return intent.toString();
    }

    private static String format(Result state) {

        switch (state) {
            case PASSED:
                return " OK ";
            case FAILED:
                return "FAIL";
            default:
                return "    ";
        }
    }

    private static String format(Rationale rationale, int indent) {

        if (rationale == null) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(indent))
            .append("- Expected : ")
            .append(rationale.expected())
            .append("\n")
            .append(indent(indent))
            .append("- Actual   : ")
            .append(rationale.actual())
            .append("\n");

        rationale.hint().ifPresent(hint ->
            builder.append(indent(indent))
                .append("- Hint     : ")
                .append(hint)
        );

        return builder.toString();
    }

    private static class RootCauseFinder {

        private final Evaluation eval;

        public RootCauseFinder(Evaluation eval) {
            this.eval = eval;
        }

        public Optional<RootCause> find() {
            return find(eval);
        }

        private static Optional<RootCause> find(Evaluation eval) {

            if (eval.result().equals(Result.PASSED)) {
                return Optional.empty();
            }
            else if (eval.result().equals(Result.FAILED) && eval.children().isEmpty()) {
                return Optional.of(
                    new RootCause(
                        eval.name(),
                        eval.rationale()
                    )
                );
            }

            for (Evaluation child : eval.children()) {

                Optional<RootCause> rootCause = find(child);

                if (rootCause.isPresent()) {
                    return rootCause;
                }
            }

            return Optional.empty();
        }
    }

    private static class RootCause {

        private final String name;
        private final Rationale rationale;

        public RootCause(String name, Rationale rationale) {
            this.name = name;
            this.rationale = rationale;
        }

        @Override
        public String toString() {
            return "Root Cause: " +
                name +
                "\n" +
                format(rationale, 0) +
                "\n";
        }
    }
}
