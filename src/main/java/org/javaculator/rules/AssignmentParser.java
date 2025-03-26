package org.javaculator.rules;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class AssignmentParser<T> extends BaseParser<T> {
    @SuppressWarnings("unchecked")
    public static <T> AssignmentParser<T> create() {
        return Parboiled.createParser(AssignmentParser.class);
    }

    public Rule digit() {
        return Sequence(
                CharRange('0', '9'),
                EOI
        );
    }

    public Rule whitespace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }
}
