package org.javaculator.rules.literals;

import org.javaculator.rules.digits.DigitRules;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class IntegerLiteralRules extends BaseParser<Object> {
    private final DigitRules digitRules = new DigitRules();

    public Rule IntegerLiteral() {
        return Sequence(
                FirstOf(
                        HexIntegerLiteral(),
                        BinaryIntegerLiteral(),
                        OctalIntegerLiteral(),
                        DecimalIntegerLiteral()
                ),
                // Optionally allow a suffix indicating a long literal (l or L)
                Optional(FirstOf('l', 'L')),
                TestNot(FirstOf('l', 'L'))
        );
    }

    Rule DecimalIntegerLiteral() {
        // Must start with a non-zero digit; then may have additional digits with optional underscores.
        return Sequence(
                digitRules.nonZeroDigit(),
                ZeroOrMore(
                        Sequence(Optional('_'), digitRules.digit())
                ),
                TestNot('_') // Fail if an underscore follows.
        );
    }

    Rule OctalIntegerLiteral() {
        // An octal literal starts with 0 and is followed by octal digits (0-7) with optional underscores.
        return Sequence(
                "0",
                OneOrMore(
                        Sequence(Optional('_'), digitRules.octalDigit())
                )
        );
    }

    Rule HexIntegerLiteral() {
        // A hexadecimal literal starts with 0x or 0X and is followed by hex digits with optional underscores.
        return Sequence(
                "0",
                FirstOf('x', 'X'),
                digitRules.hexDigit(),                // First hex digit (must be present)
                ZeroOrMore(Sequence('_', digitRules.hexDigit())), // Any underscore must be followed by a hex digit
                TestNot('_')                          // Ensure no trailing underscore
        );
    }

    Rule BinaryIntegerLiteral() {
        // A binary literal starts with 0b or 0B and is followed by binary digits (0 or 1) with optional underscores.
        return Sequence(
                "0",
                FirstOf('b', 'B'),
                digitRules.binaryDigit(),                         // First binary digit must be here
                ZeroOrMore(Sequence('_', digitRules.binaryDigit())),// Subsequent underscores must be followed by a binary digit
                TestNot('_')                                      // Ensure the literal does not end with an underscore
        );
    }
}
