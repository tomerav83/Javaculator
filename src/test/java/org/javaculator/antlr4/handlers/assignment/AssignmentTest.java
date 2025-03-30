package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @Test
        @DisplayName("test null variable assignment")
        public void testNullAssignment() {
            calculator.prepareAndInvokeCalculation("x = null");
            assertTrue(calculator.getCache().containsKey("x"));
            assertNull(calculator.getCache().get("x"));
        }

        @ParameterizedTest(name = "Test simple assignment {0}")
        @CsvSource({
                "x=0, x, 0",
                "x=+0, x, 0",
                "x=-0, x, 0",
                "x=1, x, 1",
                "x=+1, x, 1",
                "x=-1, x, -1",
                "x=123, x, 123",
                "x=+123, x, 123",
                "x=-123, x, -123",
                "x=1_234, x, 1234",
                "x=+1_234, x, 1234",
                "x=-1_234, x, -1234",
                "x=1234l, x, 1234",
                "x=+1234l, x, 1234",
                "x=-1234l, x, -1234",
                "x=987654321L, x, 987654321",
                "x=+987654321L, x, 987654321",
                "x=-987654321L, x, -987654321",

                "x=0x1, x, 1",
                "x=+0x1, x, 1",
                "x=-0x1, x, -1",
                "x=0X1, x, 1",
                "x=+0X1, x, 1",
                "x=-0X1, x, -1",
                "x=0xA, x, 10",
                "x=+0xA, x, 10",
                "x=-0xA, x, -10",
                "x=0x1_2, x, 18",
                "x=+0x1_2, x, 18",
                "x=-0x1_2, x, -18",
                "x=0xABC, x, 2748",
                "x=+0xABC, x, 2748",
                "x=-0xABC, x, -2748",
                "x=0x1A2BL, x, 6699",
                "x=+0x1A2BL, x, 6699",
                "x=-0x1A2BL, x, -6699",

                "x=01, x, 1",
                "x=+01, x, 1",
                "x=-01, x, -1",
                "x=0123, x, 83",
                "x=+0123, x, 83",
                "x=-0123, x, -83",
                "x=07, x, 7",
                "x=+07, x, 7",
                "x=-07, x, -7",
                "x=01_2, x, 10",
                "x=+01_2, x, 10",
                "x=-01_2, x, -10",
                "x=0123L, x, 83",
                "x=+0123L, x, 83",
                "x=-0123L, x, -83",
                "x=075L, x, 61",
                "x=+075L, x, 61",
                "x=-075L, x, -61",

                "x=0b0, x, 0",
                "x=+0b0, x, 0",
                "x=-0b0, x, 0",
                "x=0B1, x, 1",
                "x=+0B1, x, 1",
                "x=-0B1, x, -1",
                "x=0b1010, x, 10",
                "x=+0b1010, x, 10",
                "x=-0b1010, x, -10",
                "x=0B10_10, x, 10",
                "x=+0B10_10, x, 10",
                "x=-0B10_10, x, -10",
                "x=0b1010l, x, 10",
                "x=+0b1010l, x, 10",
                "x=-0b1010l, x, -10",
                "x=0B1010L, x, 10",
                "x=+0B1010L, x, 10",
                "x=-0B1010L, x, -10"
        })
        public void testSimpleAssignment(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=0_1, x",
                "x=+0_1, x",
                "x=-0_1, x",

                "x=1__2, x",
                "x=+1__2, x",
                "x=-1__2, x",

                "x=08, x",
                "x=+08, x",
                "x=-08, x",

                "x=0x, x",
                "x=+0x, x",
                "x=-0x, x"
        })
        public void testInvalidSimpleAssignment(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class SimpleAssignments {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=1.0, x, 1.0",
                "x=+1.0, x, 1.0",
                "x=-1.0, x, -1.0",
                "x=1.0+2.0, x, 3.0",
                "x=1_0.0, x, 10.0",
                "x=0x1.8p10, x, 1536.0",
                "x=.5, x, 0.5",
                "x=1., x, 1"
        })
        public void testValidSimpleAssignmentOperations(String input, String variable, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(variable));
        }

        @ParameterizedTest
        @CsvSource({
                "x=, x",
                "x=1.0+, x",
                "x=+ , x",
                "x==1.0, x",
                "1.0, x",
        })
        public void testInvalidSimpleAssignmentOperations(String input, String variable) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(variable));
        }
    }
}
