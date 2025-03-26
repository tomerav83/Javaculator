package org.javaculator.rules;

import org.javaculator.rules.numbers.Digits;
import org.javaculator.rules.numbers.Numbers;
import org.parboiled.Rule;

public class Parsers {
    private static final Javarser PARSER = Javarser.create();

    public static Rule number() {
        return PARSER.numberMatcher();
    }

    public static Rule whitespace() {
        return PARSER.whitespaceMatcher();
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
