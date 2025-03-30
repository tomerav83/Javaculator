package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModTest {
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
                "x=10%3, x, 1",
                "x=+10%+3, x, 1",
                "x=-10%+3, x, -1",
                "x=20%6, x, 2",          // 20 % 6 = 2
                "x=20%6%4, x, 2",        // (20 % 6) = 2, then 2 % 4 = 2
                "x=1_0%3, x, 1",         // 10 % 3 = 1 (underscores)
                "x=0xA%3, x, 1",         // hexadecimal: 0xA (10) % 3 = 1
                "x=01%2, x, 1",          // octal: 01 (1) % 2 = 1
                "x=0b1010%6, x, 4"        // binary: 0b1010 (10) % 6 = 4
        })
        public void testValidModuloOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=10%, x",       // Missing right-hand operand.
                "x=%3, x",        // Missing left-hand operand.
                "x=10%%3, x",     // Consecutive '%' operators produce an empty operand.
                "x=10% , x",      // Trailing '%' with whitespace yields an empty operand.
                "x=10%0, x"       // Modulo by zero.
        })
        public void testInvalidModuloOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
