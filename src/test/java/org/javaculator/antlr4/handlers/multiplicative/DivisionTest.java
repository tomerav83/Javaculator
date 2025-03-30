package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DivisionTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=10/2, x, 5",
                "x=+10/+2, x, 5",
                "x=-10/+2, x, -5",
                "x=20/2/2, x, 5",
                "x=1_0/2, x, 5",
                "x=0xA/2, x, 5",
                "x=01/1, x, 1",
                "x=0b1010/0b10, x, 5"
        })
        public void testValidDivisionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=10/, x",
                "x=/2, x",
                "x=10//2, x",
                "x=10/ , x",
                "x=+10/0, x",
                "x=10/0, x"
        })
        public void testInvalidDivisionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class FloatLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=6.0/2.0, x, 3",
                "x=+6.0/+2.0, x, 3",
                "x=-6.0/+2.0, x, -3",
                "x=0.0/1.0, x, 0",
                "x=12.0/2.0/3.0, x, 2",
                "x=1_2.0/2.0, x, 6",
                "x=0x1.8p10/2.0, x, 768",
                "x=.6/2.0, x, 0.3",
                "x=6./2.0, x, 3"
        })
        public void testValidFloatDivisionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=6.0/, x",
                "x=/2.0, x",
                "x=6.0//2.0, x",
                "x=6.0/ , x",
                "x=6.0/0.0, x",
                "x=6_.0/2.0, x",
                "x=6._0/2.0, x",
                "x=6.0e/2.0, x",
                "x=0x1.8/2.0, x"
        })
        public void testInvalidFloatDivisionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
