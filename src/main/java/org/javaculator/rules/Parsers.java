package org.javaculator.rules;

import org.javaculator.rules.numbers.Digits;
import org.javaculator.rules.numbers.Numbers;
import org.javaculator.rules.numbers.NumericLiterals;
import org.parboiled.Rule;

public class Parsers {
    // literals
    public static Rule integerLiteral() {
        return NumericLiterals.INSTANCE.integerLiteral();
    }

    public static Rule floatLiteral() {
        return NumericLiterals.INSTANCE.floatLiteral();
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
