package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PreIncDecTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=++x, x, 0",
                "x=++x, x, 5",
                "x=++var, var, 10",
                "x=++   x, x, 3"
        })
        void testValidPreIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue.add(BigDecimal.ONE), calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=--x, x, 0",
                "x=--x, x, 5",
                "x=--var, var, 10",
                "x=--   x, x, 3"
        })
        void testValidPreDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue.subtract(BigDecimal.ONE), calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=++, x, 0",
                "x=++1, x, 0"
        })
        public void testInvalidPreIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=--, x, 0",
                "x=--1, x, 0"
        })
        public void testInvalidPreDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=++$x, $x",
                "x=++undefined, undefined"
        })
        public void testInvalidUndefinedPreIncrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=--$x, $x",
                "x=--undefined, undefined"
        })
        public void testInvalidUndefinedPreDecrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class PreIncDecOperations {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=++x, x, 1.0, 2.0",
                "x=--x, x, 1.0, 0.0",
                "x=++x, x, -2.5, -1.5",
                "x=--x, x, -2.5, -3.5"
        })
        public void testValidPreIncDecOperations(String input, String variable, BigDecimal initialValue, BigDecimal expected) {
            calculator.getCache().put(variable, initialValue);
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(variable), "Failed for input: " + input);
        }

        @ParameterizedTest
        @CsvSource({
                "x=++, x",
                "x=++1, x",
                "x=++$x, $x",
                "x=++undefined, undefined",
                "x=--, x",
                "x=--1, x",
                "x=--$x, $x",
                "x=--undefined, undefined"
        })
        public void testInvalidPreIncDecOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
