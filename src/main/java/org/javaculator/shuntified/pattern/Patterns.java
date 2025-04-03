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
    public static final Pattern NEGATE = Pattern.compile("(?<!(\\d|\\)))-");

    public static final Pattern POST_INC_REPLACEABLE =
            Pattern.compile("postinc\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern POST_DEC_REPLACEABLE =
            Pattern.compile("postdec\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern PRE_INC_REPLACEABLE =
            Pattern.compile("preinc\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern PRE_DEC_REPLACEABLE =
            Pattern.compile("predec\\(([A-Za-z_][A-Za-z0-9_]*)\\)");
    public static final Pattern NEGATE_REPLACEABLE =
            Pattern.compile("−");

    public static final Pattern ADD = Pattern.compile("\\+");
    public static final Pattern SUB = Pattern.compile("-");
    public static final Pattern MULTIPLY = Pattern.compile("\\*");
    public static final Pattern DIV = Pattern.compile("/");
    public static final Pattern MOD = Pattern.compile("%");
    public static final Pattern ASSIGN = Pattern.compile("=");

    public static final Pattern VARIABLES =
            Pattern.compile("(?<!\\d)\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\b");

    public static final Pattern ASSIGNMENT_PATTERN = Pattern.compile(
            "(?<!\\d)\\b([a-zA-Z_$][a-zA-Z0-9_$]*)\\b\\s*(=|\\+=|-=|\\*=|/=)"
    );

    public static final Pattern NUMBERS = Pattern.compile(
            "(?:" +
                    // Decimal floating-point literals:
                    // 1. With a decimal point (digits before the dot are required):
                    "[0-9](?:_*[0-9])*\\.(?:[0-9](?:_*[0-9])*)?(?:[eE][+-]?[0-9](?:_*[0-9])*)?[fFdD]?|" +
                    // 2. Leading dot (no whole part):
                    "\\.[0-9](?:_*[0-9])*(?:[eE][+-]?[0-9](?:_*[0-9])*)?[fFdD]?|" +
                    // 3. Exponent-only (no dot, but an exponent is required):
                    "[0-9](?:_*[0-9])*[eE][+-]?[0-9](?:_*[0-9])*[fFdD]?|" +
                    // 4. Float with suffix only (if no dot or exponent):
                    "[0-9](?:_*[0-9])*[fFdD]|" +
                    // Hexadecimal floating-point literal:
                    "0[xX][0-9A-Fa-f](?:_*[0-9A-Fa-f])*\\.?" +
                    "(?:[0-9A-Fa-f](?:_*[0-9A-Fa-f])*)?\\.[pP][+-]?[0-9](?:_*[0-9])*[fFdD]?|" +
                    // Now, integer literals:
                    // Hexadecimal integer:
                    "0[xX][0-9A-Fa-f](?:_*[0-9A-Fa-f])*[lL]?|" +
                    // Binary integer:
                    "0[bB][01](?:_*[01])*[lL]?|" +
                    // Octal integer:
                    "0(?:_*[0-7])*[lL]?|" +
                    // Decimal integer:
                    "(?:0|[1-9](?:_*[0-9])*)[lL]?" +
                    ")"
    );
}
