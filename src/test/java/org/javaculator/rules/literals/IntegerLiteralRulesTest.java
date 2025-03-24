package org.javaculator.rules.literals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.parboiled.parserunners.ReportingParseRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerLiteralRulesTest {
    private final IntegerLiteralRules rules = new IntegerLiteralRules();

    @Nested
    class DecimalIntegerLiteralTests {
        private final ReportingParseRunner<Object> runner =
                new ReportingParseRunner<>(rules.DecimalIntegerLiteral());

        @ParameterizedTest(name = "Valid single digit decimal = {0}")
        @ValueSource(strings = {"1", "9"})
        void testSingleDigitDecimal(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid multi-digit decimal = {0}")
        @ValueSource(strings = {"123", "9876543210"})
        void testMultiDigitDecimal(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid decimal with underscores = {0}")
        @ValueSource(strings = {"1_234", "1_2_3_4", "1234_5678"})
        void testDecimalWithUnderscores(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Invalid decimal = {0}")
        @ValueSource(strings = {"0", "01", "_123", "123_"})
        void testInvalidDecimals(String input) {
            assertFalse(runner.run(input).matched);
        }
    }

    @Nested
    class OctalIntegerLiteralTests {
        private final ReportingParseRunner<Object> runner =
                new ReportingParseRunner<>(rules.OctalIntegerLiteral());

        @ParameterizedTest(name = "Valid octal literal = {0}")
        @ValueSource(strings = {"01", "0123", "07654321"})
        void testValidOctalLiterals(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid octal with underscores = {0}")
        @ValueSource(strings = {"0_1", "01_2_3", "0123_456"})
        void testOctalWithUnderscores(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Invalid octal literal = {0}")
        @ValueSource(strings = {"0", "08", "09", "0_", "0__1"})
        void testInvalidOctalLiterals(String input) {
            assertFalse(runner.run(input).matched);
        }
    }

    @Nested
    class HexIntegerLiteralTests {
        private final ReportingParseRunner<Object> runner =
                new ReportingParseRunner<>(rules.HexIntegerLiteral());

        @ParameterizedTest(name = "Valid hex literal with lowercase x = {0}")
        @ValueSource(strings = {"0x0", "0x123", "0xabcdef", "0xABCDEF", "0x0123456789abcdef"})
        void testValidHexLiteralsLowerX(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid hex literal with uppercase X = {0}")
        @ValueSource(strings = {"0X0", "0X123abc", "0XFFFFFF"})
        void testValidHexLiteralsUpperX(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid hex with underscores = {0}")
        @ValueSource(strings = {"0x1_2_3", "0Xa_b_c", "0xFFF_FFF"})
        void testHexWithUnderscores(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Invalid hex literal = {0}")
        @ValueSource(strings = {"0x", "0xG", "0x_1", "0x1_"})
        void testInvalidHexLiterals(String input) {
            assertFalse(runner.run(input).matched);
        }
    }

    @Nested
    class BinaryIntegerLiteralTests {
        private final ReportingParseRunner<Object> runner =
                new ReportingParseRunner<>(rules.BinaryIntegerLiteral());

        @ParameterizedTest(name = "Valid binary literal with lowercase b = {0}")
        @ValueSource(strings = {"0b0", "0b1", "0b01", "0b10", "0b1010101"})
        void testValidBinaryLiteralsLowerB(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid binary literal with uppercase B = {0}")
        @ValueSource(strings = {"0B0", "0B1", "0B0101"})
        void testValidBinaryLiteralsUpperB(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid binary with underscores = {0}")
        @ValueSource(strings = {"0b1_0", "0B1_0_1_0", "0b1010_1010"})
        void testBinaryWithUnderscores(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Invalid binary literal = {0}")
        @ValueSource(strings = {"0b", "0b2", "0b_1", "0b1_"})
        void testInvalidBinaryLiterals(String input) {
            assertFalse(runner.run(input).matched);
        }
    }

    @Nested
    class IntegerLiteralTests {
        private final ReportingParseRunner<Object> runner =
                new ReportingParseRunner<>(rules.IntegerLiteral());

        @ParameterizedTest(name = "Valid integer literal = {0}")
        @ValueSource(strings = {
                "1", "123", "1_234",
                "01", "0123", "01_234",
                "0x1", "0Xabc", "0xDEF", "0x1_2_3",
                "0b0", "0B1", "0b1010", "0b1_0_1_0"
        })
        void testValidIntegerLiterals(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Valid integer literal with long suffix = {0}")
        @ValueSource(strings = {
                "1L", "123l", "1_234L",
                "01L", "0123l", "01_234L",
                "0x1L", "0Xabcl", "0xDEFL", "0x1_2_3l",
                "0b0L", "0B1l", "0b1010L", "0b1_0_1_0l"
        })
        void testValidIntegerLiteralsWithLongSuffix(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Edge case: MAX_VALUE or MIN_VALUE = {0}")
        @ValueSource(strings = {
                "2147483647",
                "2147483647L",
                "0x7fffffff",
                "0b01111111111111111111111111111111"
        })
        void testIntegerBoundaries(String input) {
            assertTrue(runner.run(input).matched);
        }

        @ParameterizedTest(name = "Invalid integer literal = {0}")
        @ValueSource(strings = {
                "", "L", "l", "_123", "123_", "0", "0_",
                "0x", "0X", "0xZ", "0x_1", "0x1_",
                "0b", "0B", "0b2", "0b_1", "0b1_",
                "1LL", "1Ll", "1lL", "1_", "_1", "1__2"
        })
        void testInvalidIntegerLiterals(String input) {
            assertFalse(runner.run(input).matched);
        }
    }
}
