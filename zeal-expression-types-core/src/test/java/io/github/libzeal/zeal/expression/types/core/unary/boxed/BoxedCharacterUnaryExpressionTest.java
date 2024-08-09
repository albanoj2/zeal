package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpressionTest;
import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

public class BoxedCharacterUnaryExpressionTest extends ObjectUnaryExpressionTest<Character,
    BoxedCharacterUnaryExpression> {

    @Override
    protected BoxedCharacterUnaryExpression expression(final Character value) {
        return new BoxedCharacterUnaryExpression(value);
    }

    @Override
    protected Character exampleValue1() {
        return 'a';
    }

    @Override
    protected Character exampleValue2() {
        return 'b';
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        isLowerCaseTestCases(builder);
        isUpperCaseTestCases(builder);
        isLetterTestCases(builder);
        isNotLetterTestCases(builder);
        isDigitTestCases(builder);
        isNotDigitTestCases(builder);
        isLetterOrDigitTestCases(builder);
        isNotLetterOrDigitTestCases(builder);
        isWhitespaceTestCases(builder);
        isNotWhitespaceTestCases(builder);
    }

    private void isLowerCaseTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isLowerCase())
                .value('c')
                .expectedState(PASSED)
                .expectedName("isLowerCase")
                .expectedExpected("isLowerCase")
                .expectedActual("c")
                .addTest()
            .newTest((expression, value) -> expression.isLowerCase())
                .value('C')
                .expectedState(FAILED)
                .expectedName("isLowerCase")
                .expectedExpected("isLowerCase")
                .expectedActual("C")
                .addTest();
    }

    private void isUpperCaseTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isUpperCase())
                .value('c')
                .expectedState(FAILED)
                .expectedName("isUpperCase")
                .expectedExpected("isUpperCase")
                .expectedActual("c")
                .addTest()
            .newTest((expression, value) -> expression.isUpperCase())
                .value('C')
                .expectedState(PASSED)
                .expectedName("isUpperCase")
                .expectedExpected("isUpperCase")
                .expectedActual("C")
                .addTest();
    }

    private void isLetterTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isLetter())
                .value('1')
                .expectedState(FAILED)
                .expectedName("isLetter")
                .expectedExpected("isLetter")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isLetter())
                .value('C')
                .expectedState(PASSED)
                .expectedName("isLetter")
                .expectedExpected("isLetter")
                .expectedActual("C")
                .addTest();
    }

    private void isNotLetterTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotLetter())
                .value('1')
                .expectedState(PASSED)
                .expectedName("isNotLetter")
                .expectedExpected("not[isLetter]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetter())
                .value('C')
                .expectedState(FAILED)
                .expectedName("isNotLetter")
                .expectedExpected("not[isLetter]")
                .expectedActual("C")
                .addTest();
    }

    private void isDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isDigit())
                .value('1')
                .expectedState(PASSED)
                .expectedName("isDigit")
                .expectedExpected("isDigit")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isDigit())
                .value('C')
                .expectedState(FAILED)
                .expectedName("isDigit")
                .expectedExpected("isDigit")
                .expectedActual("C")
                .addTest();
    }

    private void isNotDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotDigit())
                .value('1')
                .expectedState(FAILED)
                .expectedName("isNotDigit")
                .expectedExpected("not[isDigit]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotDigit())
                .value('C')
                .expectedState(PASSED)
                .expectedName("isNotDigit")
                .expectedExpected("not[isDigit]")
                .expectedActual("C")
                .addTest();
    }

    private void isLetterOrDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isLetterOrDigit())
                .value('1')
                .expectedState(PASSED)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isLetterOrDigit())
                .value('C')
                .expectedState(PASSED)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isLetterOrDigit())
                .value('-')
                .expectedState(FAILED)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("-")
                .addTest();
    }

    private void isNotLetterOrDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotLetterOrDigit())
                .value('1')
                .expectedState(FAILED)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetterOrDigit())
                .value('C')
                .expectedState(FAILED)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetterOrDigit())
                .value('-')
                .expectedState(PASSED)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("-")
                .addTest();
    }

    private void isWhitespaceTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isWhitespace())
                .value('1')
                .expectedState(FAILED)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isWhitespace())
                .value('C')
                .expectedState(FAILED)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isWhitespace())
                .value(' ')
                .expectedState(PASSED)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual(" ")
                .addTest();
    }

    private void isNotWhitespaceTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotWhitespace())
                .value('1')
                .expectedState(PASSED)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotWhitespace())
                .value('C')
                .expectedState(PASSED)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isNotWhitespace())
                .value(' ')
                .expectedState(FAILED)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual(" ")
                .addTest();
    }
}
