package org.javaculator.antlr4.handlers.additive;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleSubtractionTest {
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
                "x=5-3, x, 2",
                "x=+5-+3, x, 2",
                "x=5-+3, x, 2",
                "x=-5-+3, x, -8",
                "x=10-2-3, x, 5",         // 10 - 2 = 8, 8 - 3 = 5
                "x=1_0-2, x, 8",          // "1_0" represents 10; 10 - 2 = 8
                "x=0xA-1, x, 9",          // hexadecimal: 0xA (10) - 1 = 9
                "x=01-1, x, 0",           // octal: 01 (1) - 1 = 0
                "x=0b1010-0b1, x, 9"       // binary: 0b1010 (10) - 0b1 (1) = 9
        })
        public void testValidSubtractionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=5-, x",       // missing right-hand operand
                "x=+5-, x",      // missing right-hand operand
                "x=5--3, x",     // consecutive '-' results in an empty operand
                "x=5- , x",      // trailing '-' with whitespace yields empty operand
                "x=-5-, x"       // negative literal with trailing '-' is invalid
        })
        public void testInvalidSubtractionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
