package org.javaculator.rules.digits;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class DigitRules extends BaseParser<Object> {
    public Rule digit() {
        return CharRange('0', '9');
    }

    public Rule nonZeroDigit() {
        return CharRange('1', '9');
    }

    public Rule octalDigit() {
        return CharRange('0', '7');
    }

    public Rule binaryDigit() {
        return FirstOf('0', '1');
    }

    public Rule hexDigit() {
        return FirstOf(digit(), CharRange('a', 'f'), CharRange('A', 'F'));
    }
}
