package org.javaculator.antlr4.handlers.additive;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SubtractionTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=5-3, x, 2",
                "x=+5-+3, x, 2",
                "x=5-+3, x, 2",
                "x=-5-+3, x, -8",
                "x=10-2-3, x, 5",
                "x=1_0-2, x, 8",
                "x=0xA-1, x, 9",
                "x=01-1, x, 0",
                "x=0b1010-0b1, x, 9"
        })
        public void testValidSubtractionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=5-, x",
                "x=+5-, x",
                "x=5--3, x",
                "x=5- , x",
                "x=-5-, x"
        })
        public void testInvalidSubtractionOperations(String input, String key) {
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
                "x=3.0-2.0, x, 1.0",
                "x=+3.0-2.0, x, 1.0",
                "x=-1.0-2.0, x, -3.0",
                "x=0.0-0.0, x, 0.0",
                "x=10.0-2.0-3.0, x, 5.0",
                "x=1_0.0-2.0, x, 8.0",
                "x=0x1.8p10-1.0, x, 1535.0",
                "x=.5-1.0, x, -0.5",
                "x=1.-2.0, x, -1.0"
        })
        public void testValidFloatSubtractionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=1.0-, x",
                "x=+1.0-, x",
                "x=1.0--2.0, x",
                "x=1.0-*2.0, x",
                "x=1.0- , x",
                "x=1_.0-2.0, x",
                "x=1._0-2.0, x",
                "x=1.0e- , x",
                "x=0x1.8-1.0, x",
        })
        public void testInvalidFloatSubtractionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
