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
        @CsvSource(
                {
                        "x=++x, x, 0",
                        "x=++x, x, 5",
                        "x=++var, var, 10",
                        "x=++   x, x, 3"
                }
        )
        void testValidPreIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue.add(BigDecimal.ONE), calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource(
                {
                        "x=--x, x, 0",
                        "x=--x, x, 5",
                        "x=--var, var, 10",
                        "x=--   x, x, 3"
                }
        )
        void testValidPreDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue.subtract(BigDecimal.ONE), calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=++, x, 0",              // Missing variable name after "++"
                "x=++1, x, 0",             // Invalid variable name (starts with digit)
        })
        public void testInvalidPreIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=--, x, 0",              // Missing variable name after "--"
                "x=--1, x, 0",             // Invalid variable name (starts with digit)
        })
        public void testInvalidPreDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=++$x, $x",           // Invalid variable name (starts with non-letter/underscore)
                "x=++undefined, undefined" // Variable not defined in the environment
        })
        public void testInvalidUndefinedPreIncrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=--$x, $x",           // Invalid variable name (starts with non-letter/underscore)
                "x=--undefined, undefined" // Variable not defined in the environment
        })
        public void testInvalidUndefinedPreDecrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
