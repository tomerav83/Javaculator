package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                    "x+=, x, 0",
                    "+=3, x, 0",
                    "x+==3, x, 0",
                    "x+=invalid, x, 0",
                    "x+=1++2, x, 0"
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
                    "x-=, x, 0",
                    "-=3, x, 0",
                    "x-==3, x, 0",
                    "x-=invalid, x, 0",
                    "x-=1++2, x, 0"
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
                    "x*=, x, 0",
                    "*=3, x, 0",
                    "x*==3, x, 0",
                    "x*=invalid, x, 0",
                    "x*=1++2, x, 0"
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
                    "x/=, x, 0",
                    "/=3, x, 0",
                    "x/==3, x, 0",
                    "x/=invalid, x, 0",
                    "x/=1++2, x, 0"
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
                    "x^=, x, 0",
                    "^=3, x, 0",
                    "x^==3, x, 0",
                    "x^=invalid, x, 0",
                    "x^=1++2, x, 0"
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
                    "x%=, x, 0",
                    "%=3, x, 0",
                    "x%==3, x, 0",
                    "x%=invalid, x, 0",
                    "x%=1++2, x, 0"
            })
            public void testInvalidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }
    }

    @Nested
    class FloatingPointLiterals {
        @Nested
        class Addition {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    "x+=3.14, x, 2, 5.14",
                    "x += +4.5, x, 10, 14.5",
                    "var+=1.5+2.5, var, 5, 9.0",
                    "x+=0.0, x, 7, 7.0",
                    "x+=-3.14, x, 7, 3.86",
                    "x+=1_000.5, x, 10, 1010.5",
                    "x+=.25, x, 1, 1.25",
                    "x+=5., x, 10, 15",
                    "x+=1.5e2, x, 10, 160",
                    "x+=1.5e+2, x, 10, 160",
                    "x+=1.5e-2, x, 1, 1.015",
                    "x+=1.5E3, x, 10, 1510",
                    "x+=0xA.5p2, x, 0, 41.25",
                    "x+=0X1.8p4, x, 0, 24.0",
                    "x+=2.5f, x, 10, 12.5",
                    "x+=3.75d, x, 5, 8.75"
            })
            public void testValidAugmentedAdditionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x+=., x, 0",
                    "x+=e5, x, 0",
                    "x+=1.5e, x, 0",
                    "x+=1.5e+, x, 0",
                    "x+=0x., x, 0",
                    "x+=0xp1, x, 0",
                    "x+=5.4.3, x, 0",
                    "x+=1.0f2, x, 0",
                    "x+=1.0ff, x, 0"
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
                    "x-=3.14, x, 2, -1.14",
                    "x -= +4.5, x, 10, 5.5",
                    "var-=1.5+2.5, var, 5, 1.0",
                    "x-=0.0, x, 7, 7.0",
                    "x-=-3.14, x, 7, 10.14",
                    "x-=1_000.5, x, 10, -990.5",
                    "x-=.25, x, 1, 0.75",
                    "x-=5., x, 10, 5",
                    "x-=1.5e2, x, 10, -140",
                    "x-=1.5e+2, x, 10, -140",
                    "x-=1.5e-2, x, 1, 0.985",
                    "x-=1.5E3, x, 10, -1490",
                    "x-=0xA.5p2, x, 0, -41.25",
                    "x-=0X1.8p4, x, 0, -24.0",
                    "x-=2.5f, x, 10, 7.5",
                    "x-=3.75d, x, 5, 1.25"
            })
            public void testValidAugmentedSubtractionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x-=., x, 0",
                    "x-=e5, x, 0",
                    "x-=1.5e, x, 0",
                    "x-=1.5e+, x, 0",
                    "x-=0x., x, 0",
                    "x-=0xp1, x, 0",
                    "x-=0x.p1, x, 0",
                    "x-=5.4.3, x, 0",
                    "x-=1.0f2, x, 0",
                    "x-=1.0ff, x, 0"
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
                    "x*=3.14, x, 2, 6.28",
                    "x *= +4.5, x, 10, 45.0",
                    "var*=1.5+2.5, var, 5, 20.0",
                    "x*=0.0, x, 7, 0.0",
                    "x*=-3.14, x, 7, -21.98",
                    "x*=1_000.5, x, 0.01, 10.005",
                    "x*=.25, x, 4, 1.00",
                    "x*=5., x, 10, 50",
                    "x*=1.5e2, x, 0.1, 15",
                    "x*=1.5e+2, x, 0.1, 15",
                    "x*=1.5e-2, x, 100, 1.500",
                    "x*=1.5E3, x, 0.01, 15",
                    "x*=0xA.5p2, x, 0.5, 20.625",
                    "x*=0X1.8p4, x, 0.5, 12.00",
                    "x*=2.5f, x, 10, 25.0",
                    "x*=3.75d, x, 2, 7.50"
            })
            public void testValidAugmentedMultiplicationAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x*=., x, 0",
                    "x*=e5, x, 0",
                    "x*=.e5, x, 0",
                    "x*=1.5e, x, 0",
                    "x*=1.5e+, x, 0",
                    "x*=0x., x, 0",
                    "x*=0xp1, x, 0",
                    "x*=0x.p1, x, 0",
                    "x*=0x1.p, x, 0",
                    "x*=5.4.3, x, 0",
                    "x*=1.0f2, x, 0",
                    "x*=1.0ff, x, 0"
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
                    "x/=3.14, x, 2, 0.6369426751592356687898089171974522",
                    "x /= +4.5, x, 9, 2",
                    "var/=1.5+2.5, var, 20, 5",
                    "x/=0.5, x, 7, 14",
                    "x/=-3.14, x, 7, -2.229299363057324840764331210191083",
                    "x/=1_000.5, x, 2001, 2",
                    "x/=.25, x, 4, 16",
                    "x/=5., x, 10, 2",
                    "x/=1.5e2, x, 300, 2.0",
                    "x/=1.5e+2, x, 300, 2.0",
                    "x/=1.5e-2, x, 0.03, 2",
                    "x/=1.5E3, x, 3000, 2.00",
                    "x/=0xA.5p2, x, 84, 2.036363636363636363636363636363636",
                    "x/=0X1.8p4, x, 48, 2",
                    "x/=2.5f, x, 10, 4",
                    "x/=3.75d, x, 15, 4"
            })
            public void testValidAugmentedDivisionAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x/=., x, 0",
                    "x/=e5, x, 0",
                    "x/=.e5, x, 0",
                    "x/=1.5e, x, 0",
                    "x/=1.5e+, x, 0",
                    "x/=0x., x, 0",
                    "x/=0xp1, x, 0",
                    "x/=0x.p1, x, 0",
                    "x/=0x1.p, x, 0",
                    "x/=5.4.3, x, 0",
                    "x/=1.0f2, x, 0",
                    "x/=1.0ff, x, 0",
                    "x/=0.0, x, 10"
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
                    "x ^= +2.5, x, 4, 32.00000000000000000000000000000000",
                    "var^=1.5, var, 4, 8.000000000000000000000000000000000",
                    "x^=0.5, x, 25, 5.000000000000000000000000000000000",
                    "x^=-1.0, x, 4, 0.25",
                    "x^=1_0.0, x, 2, 1024",
                    "x^=.5, x, 16, 4.000000000000000000000000000000000",
                    "x^=2., x, 3, 9",
                    "x^=2.0e0, x, 5, 25",
                    "x^=1.0e+1, x, 2, 1024",
                    "x^=2.0E0, x, 7, 49",
                    "x^=0xA.0p0, x, 2, 1024",
                    "x^=0X1.0p2, x, 3, 81",
                    "x^=2.0f, x, 5, 25",
                    "x^=3.0d, x, 2, 8"
            })
            public void testValidAugmentedPowerAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x^=., x, 0.0",
                    "x^=e5, x, 0",
                    "x^=.e5, x, 0",
                    "x^=1.5e, x, 0",
                    "x^=1.5e+, x, 0",
                    "x^=0x., x, 0",
                    "x^=0xp1, x, 0",
                    "x^=0x.p1, x, 0",
                    "x^=0x1.p, x, 0",
                    "x^=5.4.3, x, 0",
                    "x^=1.0f2, x, 0",
                    "x^=1.0ff, x, 0"
            })
            public void testInvalidAugmentedPowerAssignment(String input, String key, BigDecimal initialValue) {
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
                    "x%=3.0, x, 5, 2.0",
                    "x %= +4.5, x, 10, 1.0",
                    "var%=1.5, var, 5, 0.5",
                    "x%=0.5, x, 3.2, 0.2",
                    "x%=-2.5, x, 7, 2.0",
                    "x%=1_0.0, x, 23.5, 3.5",
                    "x%=.25, x, 1, 0.00",
                    "x%=5., x, 13, 3",
                    "x%=1.0e1, x, 23, 3",
                    "x%=1.0e+1, x, 23, 3",
                    "x%=1.0e-1, x, 0.35, 0.05",
                    "x%=1.0E1, x, 23, 3",
                    "x%=0xA.0p0, x, 23, 3.0",
                    "x%=0X1.0p2, x, 9, 1.0",
                    "x%=2.5f, x, 10, 0.0",
                    "x%=3.0d, x, 5, 2.0"
            })
            public void testValidAugmentedModAssignment(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x%=., x, 0",
                    "x%=e5, x, 0",
                    "x%=.e5, x, 0",
                    "x%=1.5e, x, 0",
                    "x%=1.5e+, x, 0",
                    "x%=0x., x, 0",
                    "x%=0xp1, x, 0",
                    "x%=0x.p1, x, 0",
                    "x%=0x1.p, x, 0",
                    "x%=5.4.3, x, 0",
                    "x%=1.0f2, x, 0",
                    "x%=1.0ff, x, 0",
                    "x%=0.0, x, 10"
            })
            public void testInvalidAugmentedModAssignment(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class MixedOperations {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    "x+=3.5*2.0, x, 5, 12.00",
                    "x-=4.0/2.0, x, 10, 8",
                    "x*=2.5+1.5, x, 2, 8.0",
                    "x/=6.0-2.0, x, 16, 4",
                    "x^=1.0+1.0, x, 3, 9",
                    "x%=(5.0+1.0), x, 13, 1.0",
                    "x+=3.5-2.0+1.5, x, 5, 8.0",
                    "x-=4.0*0.5/2.0, x, 10, 9.0",
                    "x*=(2.5^2.0), x, 2, 12.50",
                    "x/=6.0%(5.0-1.0), x, 16, 8"
            })
            public void testValidMixedOperations(String input, String key, BigDecimal initialValue, BigDecimal expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(expectedNewValue, calculator.getCache().get(key));
            }

            @ParameterizedTest
            @CsvSource({
                    "x+=3.5*/2.0, x, 5",
                    "x-=4.0//2.0, x, 10",
                    "x*=2.5++1.5, x, 2",
                    "x/=6.0--2.0, x, 16",
                    "x^=1.0+*1.0, x, 3",
                    "x%=(5.0+), x, 13",
                    "x+=(+3.5)-(), x, 5",
                    "x-=4.0*)0.5, x, 10",
                    "x*=(.5^), x, 2",
                    "x/=6.0%%, x, 16"
            })
            public void testInvalidMixedOperations(String input, String key, BigDecimal initialValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                assertEquals(initialValue, calculator.getCache().get(key));
            }
        }

        @Nested
        class EdgeCases {
            @BeforeEach
            void init() {
                calculator.clear();
            }

            @ParameterizedTest
            @CsvSource({
                    "x+=1e-10, x, 0, 0.0000000001",
                    "x-=1e+10, x, 0, -10000000000",
                    "x*=1e-20, x, 1, 0.00000000000000000001",
                    "x/=1e-5, x, 0.001, 1E+2",
                    "x^=0.0, x, 123.456, 1",
                    "x%=0.001, x, 0.0052, 0.0002",
                    "x+=9999999.9999, x, 0.0001, 10000000.0000",
                    "x-=0.00000001, x, 0.00000001, 0E-8",
                    "x*=0X1.fffffffffffffp1023, x, 1, 8.98846567431158E+307",
                    "x/=0X1.0p-1022, x, 1, 4.494232837155789735168697230821004E+307",
                    "x+=Infinity, x, 0, 0",
                    "x-=Infinity, x, 0, 0",
                    "x*=NaN, x, 1, NaN"
            })
            public void testFloatingPointEdgeCases(String input, String key, Object initialValue, Object expectedNewValue) {
                calculator.prepareAndInvokeCalculation("%s = %s".formatted(key, initialValue));
                calculator.prepareAndInvokeCalculation(input);
                BigDecimal result = calculator.getCache().get(key);
                if (expectedNewValue.toString().equals("NaN")) {
                    assertTrue(result.toString().equals("NaN") || result.compareTo(BigDecimal.ZERO) != 0);
                } else if (expectedNewValue.toString().equals("Infinity") || expectedNewValue.toString().equals("-Infinity")) {
                    assertEquals(expectedNewValue.toString(), result.toString());
                } else {
                    assertEquals(new BigDecimal(expectedNewValue.toString()), result);
                }
            }
        }
    }
}
