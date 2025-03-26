package org.javaculator.rules.numbers;

import org.javaculator.rules.Parsers;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressSubnodes;

import java.util.Optional;

@BuildParseTree
public class Numbers extends BaseParser<Object> {
    public static final Numbers INSTANCE = new Numbers();

    @SuppressSubnodes
    public Rule decimalNumber() {
        return FirstOf('0', Sequence(CharRange('1', '9'), ZeroOrMore(Parsers.digit())));
    }

    @SuppressSubnodes
    @MemoMismatches
    public Rule hexNumber() {
        return Sequence('0', IgnoreCase('x'), OneOrMore(Parsers.hexDigit()));
    }

    @SuppressSubnodes
    public Rule octalNumber() {
        return Sequence('0', OneOrMore(CharRange('0', '7')));
    }

    @SuppressSubnodes
    public Rule decimalFloatNumber() {
        return FirstOf(
                Sequence(OneOrMore(Parsers.digit()), '.', ZeroOrMore(Parsers.digit()), Optional(Exponent()), Optional(floatingPointSuffix())),
                Sequence('.', OneOrMore(Parsers.digit()), Optional(Exponent()), Optional(floatingPointSuffix())),
                Sequence(OneOrMore(Parsers.digit()), Exponent(), Optional(floatingPointSuffix())),
                Sequence(OneOrMore(Parsers.digit()), Optional(Exponent()), floatingPointSuffix())
        );
    }

    @SuppressSubnodes
    public Rule hexFloatNumber() {
        return Sequence(hexSignificant(), BinaryExponent(), Optional(floatingPointSuffix()));
    }

    Rule hexSignificant() {
        return FirstOf(
                Sequence(FirstOf("0x", "0X"), ZeroOrMore(Parsers.hexDigit()), '.', OneOrMore(Parsers.hexDigit())),
                Sequence(hexNumber(), Optional('.'))
        );
    }

    Rule BinaryExponent() {
        return Sequence(AnyOf("pP"), Optional(AnyOf("+-")), OneOrMore(Parsers.digit()));
    }

    Rule floatingPointSuffix() {
        return AnyOf("fFdD");
    }

    Rule Exponent() {
        return Sequence(AnyOf("eE"), Optional(AnyOf("+-")), OneOrMore(Parsers.digit()));
    }
}
