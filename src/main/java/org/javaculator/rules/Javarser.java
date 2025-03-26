package org.javaculator.rules;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class Javarser extends BaseParser<CalculationNode> {
    public static Javarser create() {
        return Parboiled.createParser(Javarser.class);
    }

    public Rule numberMatcher() {
        return Sequence(
                Sequence(
                        Optional(Ch('-')),
                        OneOrMore(digitMatcher()),
                        Optional(Ch('.'), OneOrMore(digitMatcher()))
                ),
                push(new CalculationNode(Double.parseDouble(match())))
        );
    }

    public Rule digitMatcher() {
        return CharRange('0', '9');
    }

    public Rule whitespaceMatcher() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }
}
