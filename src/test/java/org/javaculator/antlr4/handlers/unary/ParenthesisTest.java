package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
                "x=1), x",
                "x=(1, x",
                "x=(), x",
                "x=(+), x",
                "x=(1+), x",
                "x=(1+(2), x",
                "x=( ( ) ), x"
        })
        public void testInvalidParenthesizedExpressions(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class ParenthesizedExpressions {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=(1.0), x, 1.0",
                "x=(1.0+2.0), x, 3.0",
                "x=((1.0)), x, 1.0",
                "x=((1.0+2.0)+3.0), x, 6.0",
                "x=(0x1.8p10), x, 1536.0",
                "x=((.5)), x, 0.5",
                "x=(1.0+(2.0+3.0)), x, 6.0"
        })
        public void testValidParenthesizedExpressions(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=1.0), x",
                "x=(1.0, x",
                "x=(), x",
                "x=(1.0+), x",
                "x=((1.0+2.0), x",
                "x=(+), x"
        })
        public void testInvalidParenthesizedExpressions(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
