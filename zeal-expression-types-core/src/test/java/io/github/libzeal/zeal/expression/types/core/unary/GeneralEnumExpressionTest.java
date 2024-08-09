package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.types.core.unary.test.MockEnum;

@SuppressWarnings("java:S2187")
class GeneralEnumExpressionTest extends EnumUnaryExpressionTest<MockEnum> {

    @Override
    protected GeneralEnumUnaryExpression<MockEnum> expression(MockEnum value) {
        return new GeneralEnumUnaryExpression<>(value);
    }

    @Override
    protected MockEnum exampleValue1() {
        return MockEnum.YES;
    }

    @Override
    protected MockEnum exampleValue2() {
        return MockEnum.NO;
    }
}
