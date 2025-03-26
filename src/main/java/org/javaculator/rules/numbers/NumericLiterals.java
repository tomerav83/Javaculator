package org.javaculator.rules.numbers;

import org.javaculator.rules.Parsers;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressSubnodes;

@BuildParseTree
public class NumericLiterals extends BaseParser<Object> {
    public static final NumericLiterals INSTANCE = new NumericLiterals();


    public Rule numberLiteral() {
        return FirstOf(integerLiteral(), floatLiteral());
    }

    @SuppressSubnodes
    Rule integerLiteral() {
        return Sequence(FirstOf(Parsers.hexNumber(), Parsers.octalNumber(), Parsers.decimalNumber()), Optional(AnyOf("lL")));
    }

    Rule floatLiteral() {
        return FirstOf(Parsers.hexFloatNumber(), Parsers.decimalFloatNumber());
    }
}
