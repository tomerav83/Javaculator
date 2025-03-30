package org.javaculator.antlr4.handlers.literals;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest(name = "Test simple assignment {0}")
        @CsvSource({
                // -------- Decimal Literals --------
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

                // -------- Hexadecimal Literals --------
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

                // -------- Octal Literals --------
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

                // -------- Binary Literals --------
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
                // -------- Invalid: Underscore after a single '0' --------
                "x=0_1, x",
                "x=+0_1, x",
                "x=-0_1, x",

                // -------- Invalid: Multiple consecutive underscores --------
                "x=1__2, x",
                "x=+1__2, x",
                "x=-1__2, x",

                // -------- Invalid: Octal literal containing digit '8' --------
                "x=08, x",
                "x=+08, x",
                "x=-08, x",

                // -------- Invalid: Hex literal missing digits after '0x' --------
                "x=0x, x",
                "x=+0x, x",
                "x=-0x, x"
        })
        public void testInvalidSimpleAssignment(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
