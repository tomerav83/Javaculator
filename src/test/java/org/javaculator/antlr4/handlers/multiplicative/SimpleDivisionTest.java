package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleDivisionTest {
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
                "x=10/2, x, 5",
                "x=+10/+2, x, 5",
                "x=-10/+2, x, -5",
                "x=20/2/2, x, 5",          // 20/2 = 10, 10/2 = 5
                "x=1_0/2, x, 5",           // "1_0" represents 10; 10 / 2 = 5
                "x=0xA/2, x, 5",           // hexadecimal 0xA (10) / 2 = 5
                "x=01/1, x, 1",            // octal: 01 (1) / 1 = 1
                "x=0b1010/0b10, x, 5"       // binary: 0b1010 (10) / 0b10 (2) = 5
        })
        public void testValidDivisionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=10/, x",       // Missing right-hand operand.
                "x=/2, x",        // Missing left-hand operand.
                "x=10//2, x",      // Double '/' operator produces an empty operand.
                "x=10/ , x",      // Trailing '/' with whitespace yields an empty operand.
                "x=+10/0, x",     // Division by zero.
                "x=10/0, x"       // Division by zero.
        })
        public void testInvalidDivisionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
