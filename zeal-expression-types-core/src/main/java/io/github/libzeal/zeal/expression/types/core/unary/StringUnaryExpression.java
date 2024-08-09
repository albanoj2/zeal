package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.lang.predicate.ValueSupplier;

/**
 * An expression used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class StringUnaryExpression extends ObjectUnaryExpression<String, StringUnaryExpression> {

    private static final String EQUALS_OPERATOR = ":=";
    private static final String NOT_EQUALS_OPERATOR = "!=";
    private static final String LENGTH_EQUAL_PREFIX = "length " + EQUALS_OPERATOR + " ";
    private static final String OCCURS = "occurs";
    private static final String INDEX_OF_PREFIX = "indexOf";
    private static final String LAST_INDEX_OF_PREFIX = "lastIndexOf";
    private static final String OCCURRENCES_PREFIX = "occurrences";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringUnaryExpression(String subject) {
        super(subject, "String evaluation");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is empty (has a length of {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringUnaryExpression isEmpty() {
        return newPredicate(String::isEmpty)
            .name("isEmpty")
            .expected("true")
            .actual(isPredicatedPassed())
            .append();
    }

    private static ValueSupplier<String> isPredicatedPassed() {
        return (o, passed) -> passed ? "true" : "false";
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not empty (has a length greater than
     * {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringUnaryExpression isNotEmpty() {
        return newPredicate(s -> !s.isEmpty())
            .name("isNotEmpty")
            .expected("true")
            .actual(isPredicatedPassed())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isBlank() {
        return newPredicate(s -> s.trim().isEmpty())
            .name("isBlank")
            .expected("true")
            .actual(isPredicatedPassed())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isNotBlank() {
        return newPredicate(s -> !s.trim().isEmpty())
            .name("isNotBlank")
            .expected("true")
            .actual(isPredicatedPassed())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is equal to the supplied length.
     *
     * @param length
     *     The desired length of the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasLengthOf(final long length) {
        return newPredicate(s -> s.length() == length)
            .name("hasLengthOf[" + length + "]")
            .expected(LENGTH_EQUAL_PREFIX + length)
            .actual((s, passed) -> LENGTH_EQUAL_PREFIX + s.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is longer than the supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isLongerThan(final long length) {
        return newPredicate(s -> s.length() > length)
            .name("isLongerThan[" + length + "]")
            .expected("length > " + length)
            .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is longer than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isLongerThanOrEqualTo(long length) {
        return newPredicate(s -> s.length() >= length)
            .name("isLongerThanOrEqualTo[" + length + "]")
            .expected("length >= " + length)
            .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is shorter than the supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isShorterThan(long length) {
        return newPredicate(s -> s.length() < length)
            .name("isShorterThan[" + length + "]")
            .expected("length < " + length)
            .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is shorter than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isShorterThanOrEqualTo(long length) {
        return newPredicate(s -> s.length() <= length)
            .name("isShorterThanOrEqualTo[" + length + "]")
            .expected("length <= " + length)
            .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied character.
     *
     * @param c
     *     The character to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression includes(final char c) {
        return newPredicate(s -> s.indexOf(c) != -1)
            .name(includesName(c))
            .expected(includesName(c))
            .actual((s, passed) -> passed ? includesName(c) : excludesName(c))
            .hint((s, passed) -> needleInHaystackHint(s, c))
            .append();
    }

    private static String includesName(final char c){
        return "includes[" + c + "]";
    }

    private static String excludesName(final char c){
        return "excludes[" + c + "]";
    }

    private static String needleInHaystackHint(String s, char c) {

        int index = s.indexOf(c);

        if (index != -1) {
            return "'" + c + "' found at index " + index;
        }
        else {
            return "String does not include '" + c + "'";
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression includes(final CharSequence sequence) {
        return newPredicate(s -> s.contains(sequence))
            .name(includesName(sequence))
            .expected(includesName(sequence))
            .actual((s, passed) -> passed ? includesName(sequence) : excludesName(sequence))
            .hint((s, passed) -> needleInHaystackHint(s, sequence))
            .append();
    }

    private static String includesName(final CharSequence c){
        return "includes[" + c + "]";
    }

    private static String excludesName(final CharSequence c){
        return "excludes[" + c + "]";
    }

    private static String needleInHaystackHint(String s, CharSequence sequence) {

        int index = s.indexOf(sequence.toString());

        if (index != -1) {
            return "\"" + sequence + "\" found at index " + index;
        }
        else {
            return "String does not include \"" + sequence + "\"";
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied character.
     *
     * @param c
     *     The character to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression excludes(final char c) {
        return newPredicate(s -> s.indexOf(c) == -1)
            .name(excludesName(c))
            .expected(excludesName(c))
            .actual((s, passed) -> passed ? excludesName(c) : includesName(c))
            .hint((s, passed) -> needleInHaystackHint(s, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression excludes(final CharSequence sequence) {
        return newPredicate(s -> !s.contains(sequence))
            .name(excludesName(sequence))
            .expected(excludesName(sequence))
            .actual((s, passed) -> passed ? excludesName(sequence) : includesName(sequence))
            .hint((s, passed) -> needleInHaystackHint(s, sequence))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected number of occurrences.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression occursName(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) == times)
            .name(occursName(c, times, EQUALS_OPERATOR))
            .expected(OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + times)
            .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
            .append();
    }

    private static long characterCount(final String s, final char c) {
        return s.chars()
            .filter(ch -> ch == c)
            .count();
    }

    private static String occursName(final char c, final long times, final String operator) {
        return OCCURS + "[" + c + "] " + operator + " " + times;
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * greater than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression occursMoreThan(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) > times)
            .name(occursName(c, times, ">"))
            .expected(OCCURRENCES_PREFIX + " > " + times)
            .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * greater than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression occursMoreThanOrEqualTo(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) >= times)
            .name(occursName(c, times, ">="))
            .expected(OCCURRENCES_PREFIX + " >= " + times)
            .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is less
     * than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression occursLessThan(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) < times)
            .name(occursName(c, times, "<"))
            .expected(OCCURRENCES_PREFIX + " < " + times)
            .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is less
     * than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression occursLessThanOrEqualTo(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) <= times)
            .name(occursName(c, times, "<="))
            .expected(OCCURRENCES_PREFIX + " <= " + times)
            .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject starts with the supplied prefix.
     *
     * @param prefix
     *     The desired prefix.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression startsWith(final String prefix) {
        return newPredicate(s -> s.startsWith(prefix))
            .name("startsWith[" + prefix + "]")
            .expected("startsWith[" + prefix + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not start with the supplied prefix.
     *
     * @param prefix
     *     The prefix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotStartWith(final String prefix) {
        return newPredicate(s -> !s.startsWith(prefix))
            .name("doesNotStartWith[" + prefix + "]")
            .expected("not[startsWith[" + prefix + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject ends with the supplied suffix.
     *
     * @param suffix
     *     The desired suffix.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression endsWith(final String suffix) {
        return newPredicate(s -> s.endsWith(suffix))
            .name("endsWith[" + suffix + "]")
            .expected("endsWith[" + suffix + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not end with the supplied suffix.
     *
     * @param suffix
     *     The suffix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotEndWith(final String suffix) {
        return newPredicate(s -> !s.endsWith(suffix))
            .name("doesNotEndWith[" + suffix + "]")
            .expected("not[endsWith[" + suffix + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression matches the subject.
     *
     * @param regex
     *     The regular expression to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression matches(final String regex) {
        return newPredicate(s -> s.matches(regex))
            .name("matches[" + regex + "]")
            .expected("matches[" + regex + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression does not match the subject.
     *
     * @param regex
     *     The regular expression to not match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotMatch(final String regex) {
        return newPredicate(s -> !s.matches(regex))
            .name("doesNotMatch[" + regex + "]")
            .expected("not[matches[" + regex + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied argument equals the subject, ignoring case.
     *
     * @param other
     *     The string to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isCaseInsensitiveEqualTo(final String other) {
        return newPredicate(s -> s.equalsIgnoreCase(other))
            .name("caseInsensitiveEqualTo[" + other + "]")
            .expected(other)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected first index.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasAtIndex(final char needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) == index)
            .name(INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
            .expected(index)
            .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected first index.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasAtIndex(final String needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) == index)
            .name(INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
            .expected(index)
            .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotHaveAtIndex(final char needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) != index)
            .name(INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
            .expected((s, passed) -> "not[" + index + "]")
            .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotHaveAtIndex(final String needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) != index)
            .name(INDEX_OF_PREFIX  + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
            .expected((s, passed) -> "not[" + index + "]")
            .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected last index.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasAtLastIndex(final char needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) == index)
            .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
            .expected(index)
            .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected last index.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasAtLastIndex(final String needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) == index)
            .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
            .expected(index)
            .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotHaveAtLastIndex(final char needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) != index)
            .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
            .expected((s, passed) -> "not[" + index + "]")
            .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotHaveAtLastIndex(final String needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) != index)
            .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
            .expected((s, passed) -> "not[" + index + "]")
            .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
            .append();
    }
}