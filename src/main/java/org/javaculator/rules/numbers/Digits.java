package org.javaculator.rules.numbers;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class Digits extends BaseParser<Object> {
    public static final Digits INSTANCE = new Digits();

    public Rule hexDigit() {
        return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), CharRange('0', '9'));
    }

    public Rule digit() {
        return CharRange('0', '9');
    }
}
