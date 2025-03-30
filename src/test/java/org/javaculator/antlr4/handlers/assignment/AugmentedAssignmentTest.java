package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AugmentedAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @Nested
        class Addition {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x+=3, x, 2, 5",
                    "x += +4, x, 10, 14",
                    "var+=1+2, var, 5, 8",
                    "x+=0xA, x, 0, 10",
                    "x+=-3, x, 7, 4",
                    "x+=1_0, x, 10, 20",
                    "x+=1+2+3, x, 0, 6"
            })
            public void testValidAugmentedAdditionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x+=, x, 0",           // Missing right-hand side expression.
                    "+=3, x, 0",           // Missing variable name.
                    "x+==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x+=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x+=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedAdditionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class Subtraction {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x-=3, x, 2, -1",
                    "x -= +4, x, 10, 6",
                    "var-=1+2, var, 5, 2",
                    "x-=0xA, x, 0, -10",
                    "x-=-3, x, 7, 10",
                    "x-=1_0, x, 10, 0",
                    "x-=1+2+3, x, 0, -6"
            })
            public void testValidAugmentedSubtractionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x-=, x, 0",           // Missing right-hand side expression.
                    "-=3, x, 0",           // Missing variable name.
                    "x-==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x-=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x-=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedSubtractionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class Multiplication {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x*=3, x, 2, 6",
                    "x *= +4, x, 10, 40",
                    "var*=1+2, var, 5, 15",
                    "x*=0xA, x, 0, 0",
                    "x*=-3, x, 7, -21",
                    "x*=1_0, x, 10, 100",
                    "x*=1+2+3, x, 0, 0"
            })
            public void testValidAugmentedMultiplicationAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x*=, x, 0",           // Missing right-hand side expression.
                    "*=3, x, 0",           // Missing variable name.
                    "x*==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x*=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x*=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedMultiplicationAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class Division {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x/=3, x, 2, 0.6666666666666666666666666666666667",
                    "x /= +4, x, 10, 2.5",
                    "var/=1+2, var, 5, 1.666666666666666666666666666666667",
                    "x/=0xA, x, 0, 0",
                    "x/=-3, x, 7, -2.333333333333333333333333333333333",
                    "x/=1_0, x, 10, 1",
                    "x/=1+2+3, x, 0, 0"
            })
            public void testValidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x/=, x, 0",           // Missing right-hand side expression.
                    "/=3, x, 0",           // Missing variable name.
                    "x/==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x/=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x/=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class Power {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x^=3, x, 2, 8",
                    "x ^= +4, x, 10, 10000",
                    "var^=1+2, var, 5, 125",
                    "x^=0xA, x, 0, 0",
                    "x^=-3, x, 7, 0.002915451895043731778425655976676385",
                    "x^=1_0, x, 10, 10000000000",
                    "x^=1+2+3, x, 0, 0"
            })
            public void testValidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x^=, x, 0",           // Missing right-hand side expression.
                    "^=3, x, 0",           // Missing variable name.
                    "x^==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x^=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x^=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class Mod {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue, expectedNewValue
                    "x%=3, x, 2, 2",
                    "x %= +4, x, 10, 2",
                    "var%=1+2, var, 5, 2",
                    "x%=0xA, x, 0, 0",
                    "x%=-3, x, 7, 1",
                    "x%=1_0, x, 10, 0",
                    "x%=1+2+3, x, 0, 0"
            })
            public void testValidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    // Format: expression, variable, initialValue
                    "x%=, x, 0",           // Missing right-hand side expression.
                    "%=3, x, 0",           // Missing variable name.
                    "x%==3, x, 0",         // Malformed operator producing invalid RHS ("=3").
                    "x%=invalid, x, 0",     // 'invalid' is not a valid literal.
                    "x%=1++2, x, 0"        // RHS "1++2" produces an empty operand.
            })
            public void testInvalidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }
    }
}
