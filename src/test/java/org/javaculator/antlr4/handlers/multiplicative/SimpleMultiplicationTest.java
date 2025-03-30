package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleMultiplicationTest {
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
                "x=2*3, x, 6",
                "x=+2*+3, x, 6",
                "x=-2*+3, x, -6",
                "x=2*3*4, x, 24",
                "x=1_0*2, x, 20",         // "1_0" represents 10; 10 * 2 = 20
                "x=0xA*2, x, 20",          // hexadecimal: 0xA (10) * 2 = 20
                "x=01*2, x, 2",           // octal: 01 (1) * 2 = 2
                "x=0b1010*0b10, x, 20"      // binary: 0b1010 (10) * 0b10 (2) = 20
        })
        public void testValidMultiplicationOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=2*, x",       // Missing right-hand operand.
                "x=*3, x",       // Missing left-hand operand.
                "x=2**3, x",     // Double '*' operator produces an empty operand.
                "x=2* , x",      // Trailing '*' with whitespace yields empty operand.
                "x=+*3, x"       // '+' immediately followed by '*' with no literal in between.
        })
        public void testInvalidMultiplicationOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
