package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PostIncDecTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=x++, x, 0",
                "x=x++, x, 5",
                "x=   x++, x, 3"
        })
        void testValidPostIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=x--, x, 0",
                "x=x--, x, 5",
                "x=   x--, x, 3"
        })
        void testValidPostDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=var++, var, 10",
                "x=var--, var, 10"
        })
        void testValidPostIncDecEdgeOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertNotEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=++, x, 0",
                "x=1++, x, 0"
        })
        public void testInvalidPostIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=--, x, 0",
                "x=1--, x, 0"
        })
        public void testInvalidPostDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=$x++, $x",
                "x=undefined++, undefined"
        })
        public void testInvalidUndefinedPostIncrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=--$x, $x",
                "x=--undefined, undefined"
        })
        public void testInvalidUndefinedPostDecrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class PostIncDecOperations {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=x++, x, 10.0, 10.0",
                "x=x--, x, 10.0, 10.0",
                "y=y++, y, -2.5, -2.5",
                "y=y--, y, -2.5, -2.5"
        })
        public void testValidPostIncDecOperations(String input, String variable, BigDecimal initialValue, BigDecimal expectedUpdatedValue) {
            calculator.getCache().put(variable, initialValue);
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expectedUpdatedValue, calculator.getCache().get(variable));
        }

        @ParameterizedTest
        @CsvSource({
                "x=x+++ , x, 10.0",
                "x=x+-- , x, 10.0",
                "x=+x++, x, 10.0",
                "x=x++x, x, 10.0"
        })
        public void testInvalidPostIncDecOperations(String input, String variable) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(variable));
        }
    }
}
