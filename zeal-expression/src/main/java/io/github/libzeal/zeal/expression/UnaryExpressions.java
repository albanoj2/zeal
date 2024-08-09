package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.types.core.unary.GeneralEnumUnaryExpression;
import io.github.libzeal.zeal.expression.types.core.unary.GeneralObjectExpression;
import io.github.libzeal.zeal.expression.types.core.unary.StringUnaryExpression;
import io.github.libzeal.zeal.expression.types.core.unary.boxed.*;

/**
 * A class containing static methods that wrap common values as unary expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class UnaryExpressions {

    private UnaryExpressions() {
    }

    /**Integer
     * Wraps an object as an expression. This method is used when a more-specific expression is not available.
     *
     * @param value
     *     The value to wrap.
     * @param <T>
     *     The type of the object being wrapped.
     *
     * @return An expression that wraps the supplied value.
     */
    public static <T> GeneralObjectExpression<T> value(final T value) {
        return new GeneralObjectExpression<>(value);
    }

    /**
     * Wraps a {@link Enum<T>} as an expression.
     *
     * @param value
     *     The {@link Enum<T>} to wrap.
     * @param <T>
     *     The type of the enum.
     *
     * @return An expression that wraps the supplied {@link Enum<T>}.
     */
    public static <T extends Enum<T>> GeneralEnumUnaryExpression<T> value(final T value) {
        return new GeneralEnumUnaryExpression<>(value);
    }

    /**
     * Wraps a {@link String} as an expression.
     *
     * @param value
     *     The {@link String} to wrap.
     *
     * @return An expression that wraps the supplied {@link String}.
     */
    public static StringUnaryExpression value(final String value) {
        return new StringUnaryExpression(value);
    }

    /**
     * Wraps a {@link Long} as an expression.
     *
     * @param value
     *     The {@link Long} to wrap.
     *
     * @return An expression that wraps the supplied {@link Long}.
     */
    public static BoxedLongUnaryExpression value(final Long value) {
        return new BoxedLongUnaryExpression(value);
    }

    /**
     * Wraps a {@link Integer} as an expression.
     *
     * @param value
     *     The {@link Integer} to wrap.
     *
     * @return An expression that wraps the supplied {@link Integer}.
     */
    public static BoxedIntegerUnaryExpression value(final Integer value) {
        return new BoxedIntegerUnaryExpression(value);
    }

    /**
     * Wraps a {@link Double} as an expression.
     *
     * @param value
     *     The {@link Double} to wrap.
     *
     * @return An expression that wraps the supplied {@link Double}.
     */
    public static BoxedDoubleUnaryExpression value(final Double value) {
        return new BoxedDoubleUnaryExpression(value);
    }

    /**
     * Wraps a {@link Float} as an expression.
     *
     * @param value
     *     The {@link Float} to wrap.
     *
     * @return An expression that wraps the supplied {@link Float}.
     */
    public static BoxedFloatUnaryExpression value(final Float value) {
        return new BoxedFloatUnaryExpression(value);
    }

    /**
     * Wraps a {@link Boolean} as an expression.
     *
     * @param value
     *     The {@link Boolean} to wrap.
     *
     * @return An expression that wraps the supplied {@link Boolean}.
     */
    public static BoxedBooleanUnaryExpression value(final Boolean value) {
        return new BoxedBooleanUnaryExpression(value);
    }

    /**
     * Wraps a {@link Byte} as an expression.
     *
     * @param value
     *     The {@link Byte} to wrap.
     *
     * @return An expression that wraps the supplied {@link Byte}.
     */
    public static BoxedByteUnaryExpression value(final Byte value) {
        return new BoxedByteUnaryExpression(value);
    }

    /**
     * Wraps a {@link Character} as an expression.
     *
     * @param value
     *     The {@link Character} to wrap.
     *
     * @return An expression that wraps the supplied {@link Character}.
     */
    public static BoxedCharacterUnaryExpression value(final Character value) {
        return new BoxedCharacterUnaryExpression(value);
    }
}
