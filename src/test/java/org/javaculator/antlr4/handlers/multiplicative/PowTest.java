package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PowTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=2^3, x, 8",
                "x=+2^+3, x, 8",
                "x=-2^+3, x, -8",
                "x=2^-3, x, 0.125",
                "x=2^3^2, x, 64",
                "x=1_0^2, x, 100",
                "x=0x2^2, x, 4",
                "x=01^2, x, 1",
                "x=0b10^0b11, x, 8"
        })
        public void testValidPowerOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=2^, x",
                "x=^3, x",
                "x=2^^3, x",
                "x=2^ , x"
        })
        public void testInvalidPowerOperations(String input, String key) {
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
                "x=2.0^3.0, x, 8.000",
                "x=+2.0^+3.0, x, 8.000",
                "x=-2.0^+3.0, x, -8.000",
                "x=2.0^3.0^2.0, x, 64.000000",
                "x=1_0.0^2.0, x, 100.00",
                "x=0x1.8p10^2.0, x, 2359296.00",
                "x=.5^2.0, x, 0.25",
                "x=1.^2.0, x, 1"
        })
        public void testValidFloatPowerOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=2.0^, x",
                "x=^3.0, x",
                "x=2.0^^3.0, x",
                "x=2.0^*3.0, x",
                "x=2.0^ , x",
                "x=2_.0^3.0, x",
                "x=2._0^3.0, x",
                "x=2.0e^3.0, x",
                "x=0x1.8^3.0, x"
        })
        public void testInvalidFloatPowerOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
