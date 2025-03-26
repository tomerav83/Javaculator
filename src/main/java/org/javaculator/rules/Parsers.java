package org.javaculator.rules;

import org.javaculator.rules.numbers.Digits;
import org.javaculator.rules.numbers.Numbers;
import org.javaculator.rules.numbers.NumericLiterals;
import org.javaculator.rules.numeric.expression.NumericExpression;
import org.parboiled.Rule;

public class Parsers {
    // numeric expression
    public static Rule numericExpression() {
        return NumericExpression.INSTANCE.expression();
    }

    // literals
    public static Rule numberLiteral() {
        return NumericLiterals.INSTANCE.numberLiteral();
    }

    // numbers
    public static Rule decimalNumber() {
        return Numbers.INSTANCE.decimalNumber();
    }

    public static Rule decimalFloatNumber() {
        return Numbers.INSTANCE.decimalFloatNumber();
    }

    public static Rule octalNumber() {
        return Numbers.INSTANCE.octalNumber();
    }

    public static Rule hexNumber() {
        return Numbers.INSTANCE.hexNumber();
    }

    public static Rule hexFloatNumber() {
        return Numbers.INSTANCE.hexFloatNumber();
    }

    // digits
    public static Rule digit() {
        return Digits.INSTANCE.digit();
    }

    public static Rule hexDigit() {
        return Digits.INSTANCE.hexDigit();
    }
}
