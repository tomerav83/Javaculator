package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PostIncDecTest {
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
                        "x=x++, x, 0",
                        "x=x++, x, 5",
                        "x=   x++, x, 3"
                }
        )
        void testValidPostIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource(
                {
                        "x=x--, x, 0",
                        "x=x--, x, 5",
                        "x=   x--, x, 3"
                }
        )
        void testValidPostDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource(
                {
                        "x=var++, var, 10",
                        "x=var--, var, 10",
                }
        )
        void testValidPostIncDecEdgeOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertNotEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=++, x, 0",              // Missing variable name after "++"
                "x=1++, x, 0",             // Invalid variable name (starts with digit)
        })
        public void testInvalidPostIncrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=--, x, 0",              // Missing variable name after "--"
                "x=1--, x, 0",             // Invalid variable name (starts with digit)
        })
        public void testInvalidPostDecrementOperations(String input, String key, BigDecimal initialValue) {
            calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(initialValue, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=$x++, $x",           // Invalid variable name (starts with non-letter/underscore)
                "x=undefined++, undefined" // Variable not defined in the environment
        })
        public void testInvalidUndefinedPostIncrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, initialValue
                "x=--$x, $x",           // Invalid variable name (starts with non-letter/underscore)
                "x=--undefined, undefined" // Variable not defined in the environment
        })
        public void testInvalidUndefinedPostDecrementOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
