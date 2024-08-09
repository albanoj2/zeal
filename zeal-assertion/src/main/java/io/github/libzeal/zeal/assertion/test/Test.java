package io.github.libzeal.zeal.assertion.test;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.expression.UnaryExpressions.value;

public class Test {

    public static void main(String[] args) {
        require(value(Foo.FOO).is(Foo.FOO).ordinalIs(2), "Foo");
    }

    private enum Foo {
        FOO,
        BAR
    }
}
