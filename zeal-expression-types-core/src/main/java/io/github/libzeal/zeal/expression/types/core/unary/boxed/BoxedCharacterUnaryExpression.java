package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;

/**
 * An expression used to evaluate {@link Character} instances.
 *
 * @author Justin Albano
 * @implNote Many of the predicates of this expression require unboxing, resulting in some performance loss.
 * @since 0.2.0
 */
public class BoxedCharacterUnaryExpression extends ObjectUnaryExpression<Character, BoxedCharacterUnaryExpression> {

    private static final String IS_LOWER_CASE = "isLowerCase";
    private static final String IS_UPPER_CASE = "isUpperCase";
    private static final String IS_LETTER = "isLetter";
    private static final String IS_DIGIT = "isDigit";
    private static final String IS_LETTER_OR_DIGIT = "isLetterOrDigit";
    private static final String IS_WHITESPACE = "isWhitespace";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedCharacterUnaryExpression(final Character subject) {
        super(subject, "Character expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is lower case.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isLowerCase(char)
     */
    public BoxedCharacterUnaryExpression isLowerCase() {
        return newPredicate(Character::isLowerCase)
            .name(IS_LOWER_CASE)
            .expected(IS_LOWER_CASE)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is upper case.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isUpperCase(char)
     */
    public BoxedCharacterUnaryExpression isUpperCase() {
        return newPredicate(Character::isUpperCase)
            .name(IS_UPPER_CASE)
            .expected(IS_UPPER_CASE)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is a letter.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isLetter(char)
     */
    public BoxedCharacterUnaryExpression isLetter() {
        return newPredicate(Character::isLetter)
            .name(IS_LETTER)
            .expected(IS_LETTER)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a letter.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isLetter(char)
     */
    public BoxedCharacterUnaryExpression isNotLetter() {
        return newPredicate(s -> !Character.isLetter(s))
            .name("isNotLetter")
            .expected("not[" + IS_LETTER + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is a digit.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isDigit(char)
     */
    public BoxedCharacterUnaryExpression isDigit() {
        return newPredicate(Character::isDigit)
            .name(IS_DIGIT)
            .expected(IS_DIGIT)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a digit.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isDigit(char)
     */
    public BoxedCharacterUnaryExpression isNotDigit() {
        return newPredicate(s -> !Character.isDigit(s))
            .name("isNotDigit")
            .expected("not[" + IS_DIGIT + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is a letter or a digit.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isLetterOrDigit(char)
     */
    public BoxedCharacterUnaryExpression isLetterOrDigit() {
        return newPredicate(Character::isLetterOrDigit)
            .name(IS_LETTER_OR_DIGIT)
            .expected(IS_LETTER_OR_DIGIT)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a letter or a digit.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isLetterOrDigit(char)
     */
    public BoxedCharacterUnaryExpression isNotLetterOrDigit() {
        return newPredicate(s -> !Character.isLetterOrDigit(s))
            .name("isNotLetterOrDigit")
            .expected("not[" + IS_LETTER_OR_DIGIT + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is a whitespace.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isWhitespace(char)
     */
    public BoxedCharacterUnaryExpression isWhitespace() {
        return newPredicate(Character::isWhitespace)
            .name(IS_WHITESPACE)
            .expected(IS_WHITESPACE)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a whitespace.
     *
     * @return This expression (fluent interface).
     *
     * @see Character#isWhitespace(char)
     */
    public BoxedCharacterUnaryExpression isNotWhitespace() {
        return newPredicate(s -> !Character.isWhitespace(s))
            .name("isNotWhitespace")
            .expected("not[" + IS_WHITESPACE + "]")
            .append();
    }
}
