<p align="center">
  <img alt="Zeal Logo" src="/assets/zeal-logo.svg" />
</p>

# Zeal
Zeal is a Java assertion library built on a rich expression language. Simple, extensible, no fuss.

## Adding It
The Maven dependency is:

```xml
<dependency>
    <groupId>io.github.libzeal</groupId>
    <artifactId>zeal-assertion</artifactId>
    <version>0.2.0</version>
</dependency>
```

The Gradle dependency is:

```groovy
implementation 'io.github.libzeal:zeal-assertion:0.2.0'
```

## Using It
A simple Zeal precondition can be written on a single line:

```java
require(value("foo").isNotNull().isNotBlank());
```

The `value` method creates an evaluation from the string `foo`, and the `require` method evaluates that expression. The `isNotNull()` and `isNotBlank()` calls add two predicates to the expression that are checked when it is evaluated.

While many assertion libraries are available, Zeal is designed on the principle that expressions are separated from their evaluation. Thus, the same expression can be evaluated differently -- such as a precondition or a postcondition -- and custom expressions can be supplied to any evaluator. For example, the same expression above can be evaluated as a postcondition using the `ensure` method:

```java
ensure(value("foo").isNotNull().isNotBlank());
```

Likewise, we can evaluate any expression as a precondition:

```java
UnaryExpression<String> expression = // ...
require(expression);
```

## Goals
1. **Simple**: Expression and evaluators clearly document developer's intent.
2. **Portable**: Written in Java 8, OSGi compliant, and under 100 KB
3. **Independent**: No external dependencies
4. **Extensible**: Provides expressions for common types, but makes creating new expressions easy

## The Basics
Zeal is based a few main concepts:
1. Evaluators
2. Expressions
3. Formatters

### Evaluators
An evaluator is a method that evaluates an expression, such as an assertion. Zeal has three basic types of assertions:

```java
confirm(expression)     // Confirmation (simple assertion)
require(expression)     // Precondition
ensure(expression)      // Postcondition
```

All of these are static methods of the following class:

```java
io.github.libzeal.zeal.assertion.Assertions
```

#### Confirmations
A confirmation is the equivalent of a basic `assert` and is created using the `confirm` method, which:
* Throws an `AssertionFailedException` if the expression evaluates to false
* Returns the subject of a `UnaryExpression` if the expression evaluates to true
* Returns `void` for all expressions if the expression evaluates to true

`AssertionFailedException` is a subclass of [`IllegalStateException`](https://docs.oracle.com/en/java/javase/21/docs/api///java.base/java/lang/IllegalStateException.html). An example of a simple assertion is:

```java
public String processName(String name) {
    
    String firstName = extractFirstName(name);
    
    confirm(value(name).startsWith(firstName));
    
    return "Hello, " + firstName;
}
```

A custom message can be added to the `AssertionFailedException` thrown when the assertion fails:

```java
confirm(value(name).startsWith(firstName), "First name must be the first name in a full name");
```

A confirmation can be configured to throw a different exception or use a different formatter by instantiating a 
`Confirmation` object and calling the `confirm` method on that object:

```java
import io.github.libzeal.zeal.assertion.Confirmation;

Confirmation assertion = Confirmation.create()
    .thatThrowsOnFail(InvalidNameException::new)
    .withFormatter(customFormatter);

assertion.confirm(value(name).startsWith(firstName));
```

#### Preconditions
A precondition is created using the `require` method, which:
* Throws a `NullPointerException` (NPE) if the expression fails and the subject is `null`
* Throws a `PreconditionFailedException` if the expression fails for any other reason
* Returns the subject of a `UnaryExpression` if the expression evaluates to true
* Returns `void` for all expressions if the expression evaluates to true

An example of a precondition is:

```java
class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = require(value(username).isNotNull().isNotBlank());
        this.password = require(value(password).isNotNull().isLongerThan(8), "Password must has sufficient length");
    }

    // ...other methods...
}
```

The [convention](https://stackoverflow.com/q/3881/2403253) is to throw an NPE if an argument is 
`null` when it is expected to be not `null`. Therefore, `require` will throw an NPE if the subject of the expression is `null`. A `PreconditionFailedException` (which is a subclass of 
`IllegalArgumentException`) will be thrown when an expression is evaluated to false in all other cases.

> **Note**: The JDK throws an NPE for `Objects.requireNonNull()` and this has become the convention. There 
> is controversy about whether an NPE or `IllegalArgumentException` should be thrown. The convention of an NPE is 
> used by default and an option is provided for throwing another exception on `null` (see `Requirement.
> thatThrowsOnNull` below).

This default behavior can be changed by instantiating a `Requirement` object:

```java
import io.github.libzeal.zeal.assertion.Requirement;

Requirement assertion = Requirement.create()
    .thatThrowsOnNull(MyCustomException::new)
    .thatThrowsOnFail(AnotherCustomExcetpion::new)
    .withFormatter(customFormatter);

assertion.require(value(username).isNotNull().isNotBlank());
```

A simplified method is provided on `Requirement` that configures the assertion to throw an 
`IllegalArgumentException` on `null`, since this is such a common use case:

```java
Requirement.thatThrowsIllegalArgumentExceptionOnNull();
```

#### Postconditions

### Expressions
#### Unary Expressions
#### Nullity

### Formatters

---
Old stuff....

Zeal is based on the concept of an expression, particularly unary expressions. A unary expression is a statement about a single subject that evaluates to true or false. For example, if we look at the sample from before:

```java
require(value("foo").isNotNull().isNotBlank());
```

### Expressions
`value("foo").isNotNull().isNotBlank()` is the expression, while `foo` is the subject of the expression. `isNotNull()` and `isNotBlank()` are predicates, which evaluate a single subject. The `value` method creates a unary expression from the supplied argument. The resulting expression is based on the type of the argument. For example, `value("foo")` results in a `StringUnaryExpression` while `value(1.0)` results in a `BoxedDoubleUnaryExpression`. Each expression has predicates available that can be added to the expression. For example,  `StringUnaryExpression` has `isNotBlank()`, while `BoxedDoubleExpression` has `isGreaterThan(Double other)`.

> **Note**: Calling a predicate method does not perform any evaluation on the subject.

For example, calling `isNotBlank()` does not immediately check if the subject is blank. Instead, calling the predicate method adds a predicate to the predicate chain for that expression. When the expression is evaluated, the predicates are evaluated in order and if a predicate fails, then an assertion is thrown. The types of exceptions thrown will depend on the specific evaluator.

#### Unary Expressions
Unary expressions are a special kind of expression because they allow us to return a single subject. For example, we can obtain the subject of a unary expression by calling the `subject()` method:

```java
UnaryExpression<String> expression = value("foo");
String subject = expression.subject();
```

This may not seem useful, but access to a single subject allows us to return the subject from an evaluator. For example, the subject of an expression can be obtained from the `require` method if all of its predicates pass:

```java
class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = require(value(username).isNotNull().isNotBlank());
        this.password = require(value(password).isNotNull().isLongerThan(8));
    }

    // ...other methods...
}
```

#### The Rule of Nullity
There is one exception to the rule that predicates are evaluated in order: `isNotNull()`. This predicate is a unique predicate because all other predicates depend on this one being true. For example, if we try to evaluate if a `Double` is greater than some value, it will fail if the subject is `null`. It is important to note that the evaluation did not strictly fail because the subject was not greater than the supplied value, but rather, because the subject was `null`. Therefore, Zeal has a special rule:

> Rule of Nullity: If an expression has a `isNotNull()` predicate, this predicate is evaluated before any other predicate.

All other predicates are evaluated in order.

### Preconditions

### Postconditions

### Assertions

## Supported Types
The following types have equivalent expressions supported in the [core types](/zeal-expression-types-core) module:
* `Object`
* `String`
* `Enum`
* `Long`
* `Integer`
* `Short`
* `Double`
* `Float`
* `Number`
* `Boolean`
* `Byte`
* `Character`
