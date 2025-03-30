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
                "x=10%3, x, 1",
                "x=+10%+3, x, 1",
                "x=-10%+3, x, -1",
                "x=20%6, x, 2",
                "x=20%6%4, x, 2",
                "x=1_0%3, x, 1",
                "x=0xA%3, x, 1",
                "x=01%2, x, 1",
                "x=0b1010%6, x, 4"
        })
        public void testValidModuloOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=10%, x",
                "x=%3, x",
                "x=10%%3, x",
                "x=10% , x",
                "x=10%0, x"
        })
        public void testInvalidModuloOperations(String input, String key) {
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
                "x=10.0%3.0, x, 1.0",
                "x=+10.0%+3.0, x, 1.0",
                "x=-10.0%+3.0, x, -1.0",
                "x=10.0%0.5, x, 0.0",
                "x=1_0.0%3.0, x, 1.0",
                "x=0x1.8p10%5.0, x, 1.0",
                "x=.5%0.2, x, 0.1",
                "x=1.%0.5, x, 0.0"
        })
        public void testValidFloatModuloOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=10.0%, x",
                "x=%3.0, x",
                "x=10.0%%3.0, x",
                "x=10.0% , x",
                "x=10_.0%3.0, x",
                "x=10._0%3.0, x",
                "x=10.0e%3.0, x",
                "x=0x1.8%3.0, x"
        })
        public void testInvalidFloatModuloOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
