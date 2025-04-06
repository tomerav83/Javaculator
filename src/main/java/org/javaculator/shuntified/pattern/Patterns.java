package org.javaculator.shuntified.pattern;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern PRE_DEC =
            Pattern.compile("(?<!\\w)--\\s*([A-Za-z_][A-Za-z0-9_]*)");
    public static final Pattern PRE_INC =
            Pattern.compile("(?<!\\w)\\+\\+\\s*([A-Za-z_][A-Za-z0-9_]*)");
    public static final Pattern POST_DEC =
            Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)\\s*--");
    public static final Pattern POST_INC =
            Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)\\s*\\+\\+");
    public static final Pattern NEGATE = Pattern.compile("(?<!(\\d|[eE]|[pP]|-))-(?!=)");

    public static final Pattern POST_INCREMENT =
            Pattern.compile("postinc\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern POST_DECREMENT =
            Pattern.compile("postdec\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern PRE_INCREMENT =
            Pattern.compile("preinc\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern PRE_DECREMENT =
            Pattern.compile("predec\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern NEGATION = Pattern.compile("−");

    public static final Pattern VARIABLES =
            Pattern.compile("(?<![a-zA-Z0-9_$])[a-zA-Z_$][a-zA-Z0-9_$]*(?![a-zA-Z0-9_$])");

    public static final Pattern BINARY = Pattern.compile("\\b0B[01]+L?\\b", Pattern.CASE_INSENSITIVE);
    public static final Pattern OCTAL = Pattern.compile("\\b0[0-7]+L?\\b", Pattern.CASE_INSENSITIVE);
    public static final Pattern DECIMAL = Pattern.compile("\\b[0-9]+L?\\b", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEXADECIMAL = Pattern.compile("\\b0[xX][0-9a-fA-F]+L?\\b", Pattern.CASE_INSENSITIVE);
    public static final Pattern DECIMAL_FP = Pattern.compile("(?:[0-9]+\\.[0-9]*|\\.[0-9]+)(?:[eE][+\\-]?[0-9]+)?[fFdD]?|[0-9]+[eE][+\\-]?[0-9]+[fFdD]?|[0-9]+[fFdD]", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEXADECIMAL_FP = Pattern.compile("0[xX](?:[0-9a-fA-F]+(?:\\.[0-9a-fA-F]*)?|\\.[0-9a-fA-F]+)(?:[pP][+\\-]?[0-9]+)?[fFdD]?", Pattern.CASE_INSENSITIVE);
}
