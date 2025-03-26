package org.javaculator.rules;

import org.parboiled.Rule;

public class Parsers {
    private static final AssignmentParser<?> PARSER = AssignmentParser.create();

    public static Rule whitespace() {
        return PARSER.whitespace();
    }

    public static Rule digit() {
        return PARSER.digit();
    }
}
