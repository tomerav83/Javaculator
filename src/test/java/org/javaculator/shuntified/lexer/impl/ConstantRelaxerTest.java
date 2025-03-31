package org.javaculator.shuntified.lexer.impl;

import org.javaculator.shuntified.token.impl.ConstantToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ConstantRelaxerTest {
    private final ConstantsRelaxer lexer = new ConstantsRelaxer();

    @ParameterizedTest(name = "Valid decimal number formats should match the pattern: {0}")
    @ValueSource(strings = {
            "0",            // Single zero
            "42",           // Integer
            "1234567890",   // Multi-digit integer
            "0.0",          // Zero with decimal
            "0.1",          // Decimal less than one
            "1.0",          // Integer with trailing zero
            "3.14159",      // PI
            "999999.999999" // Large number with decimals
    })
    void validDecimalNumbers(String input) {
        ConstantToken actual = lexer.readToken(input, 0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(input, actual.getInput()),
                () -> Assertions.assertEquals(Double.parseDouble(input), actual.getValue())
        );
    }

    @ParameterizedTest(name = "Invalid decimal number formats should not match the pattern: {0}")
    @ValueSource(strings = {
            "",
            ".",
            ".5",
            "-1",
            "+1",
            "e5",
            ",000",
            "one",
            "$1.00"
    })
    void invalidDecimalNumbers(String input) {
        Assertions.assertNull(lexer.readToken(input, 0));
    }
}
