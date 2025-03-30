package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimplePowTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, expectedValue
                "x=2^3, x, 8",
                "x=+2^+3, x, 8",
                "x=-2^+3, x, -8",
                "x=2^-3, x, 0.125",
                "x=2^3^2, x, 64",   // left-associative: (2^3)^2 = 8^2 = 64
                "x=1_0^2, x, 100",  // "1_0" represents 10, so 10^2 = 100
                "x=0x2^2, x, 4",    // hexadecimal 0x2 (2) ^ 2 = 4
                "x=01^2, x, 1",     // octal: 01 represents 1, so 1^2 = 1
                "x=0b10^0b11, x, 8" // binary: 0b10 (2) ^ 0b11 (3) = 8
        })
        public void testValidPowerOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=2^, x",      // Missing right-hand operand.
                "x=^3, x",      // Missing left-hand operand.
                "x=2^^3, x",    // Consecutive '^' operators produce an empty operand.
                "x=2^ , x"      // Trailing '^' with whitespace yields an empty operand.
        })
        public void testInvalidPowerOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
