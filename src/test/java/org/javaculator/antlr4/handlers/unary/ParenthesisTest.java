package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ParenthesisTest {
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
                "x=(1), x, 1",
                "x=( 1 ), x, 1",
                "x=((1)), x, 1",
                "x=(1+2), x, 3",
                "x=((1+2)+3), x, 6",
                "x=(+1), x, 1",
                "x=(-1), x, -1",
                "x=(((1))), x, 1",
                "x=((1+2)+(3+4)), x, 10",
                "x=(0xA), x, 10",
                "x=(01), x, 1",
                "x=(0b1010), x, 10",
                "x=(((1+2)+3)+(4+5)), x, 15",
                "x=( ( (  7 ) ) ), x, 7"
        })
        public void testValidParenthesizedExpressions(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=1), x",           // Missing opening parenthesis.
                "x=(1, x",           // Missing closing parenthesis.
                "x=(), x",           // Empty parentheses.
                "x=(+), x",          // Invalid literal inside parentheses.
                "x=(1+), x",         // Trailing operator.
                "x=(1+(2), x",       // Unbalanced parentheses.
                "x=( ( ) ), x"       // Inner parentheses empty.
        })
        public void testInvalidParenthesizedExpressions(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
