package org.javaculator.rules.numbers;

import org.javaculator.rules.Parsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.parboiled.parserunners.ReportingParseRunner;

public class NumbersTest {
    @Nested
    class DecimalNumberTest {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.decimalNumber());

        /**
         * Test cases for decimalNumber() method
         *
         * @param input Decimal number to test
         */
        @ParameterizedTest(name = "Decimal Number Match Test: [{0}] should be recognized as a decimal number")
        @ValueSource(strings = {
                "0",        // Zero
                "5",        // Single digit
                "42",       // Multi-digit number
                "123456",   // Larger number
                "09"        // Leading zero with digit (not valid in strict parsing)
        })
        void shouldMatchDecimalNumber(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for decimalNumber() method that should not match
         *
         * @param input Invalid decimal number to test
         */
        @ParameterizedTest(name = "Decimal Number Non-Match Test: [{0}] should not be recognized as a decimal number")
        @ValueSource(strings = {
                "",         // Empty string
                "a",        // Letter
                "-5",       // Negative number
                " 5"        // Whitespace
        })
        void shouldNotMatchDecimalNumber(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }

    @Nested
    class HexNumberTests {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.hexNumber());

        /**
         * Test cases for hexNumber() method
         *
         * @param input Hex number to test
         */
        @ParameterizedTest(name = "Hex Number Match Test: [{0}] should be recognized as a hex number")
        @ValueSource(strings = {
                "0x0",      // Zero
                "0x5",      // Single digit
                "0xA",      // Uppercase hex
                "0xf",      // Lowercase hex
                "0x123456", // Longer hex number
                "0xABCDEF", // Mixed case hex
                "0x0123",   // Mixed digits
                "0X123",    // Capital X
        })
        void shouldMatchHexNumber(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for hexNumber() method that should not match
         *
         * @param input Invalid hex number to test
         */
        @ParameterizedTest(name = "Hex Number Non-Match Test: [{0}] should not be recognized as a hex number")
        @ValueSource(strings = {
                "0",        // Plain zero
                "x123",     // Missing 0
                "0xG",      // Invalid hex digit
                "0x 123",   // Whitespace
                "123",      // Decimal number
                "0x"        // Incomplete hex
        })
        void shouldNotMatchHexNumber(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }

    @Nested
    class OctalNumberTest {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.octalNumber());

        @ParameterizedTest(name = "Octal Number Match Test: [{0}] should be recognized as an octal number")
        @ValueSource(strings = {
                "00",       // Zero
                "01",       // Single digit
                "012",      // Multi-digit octal
                "07",       // Highest valid octal digit
                "01234567"  // Longer octal number
        })
        void shouldMatchOctalNumber(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for octalNumber() method that should not match
         *
         * @param input Invalid octal number to test
         */
        @ParameterizedTest(name = "Octal Number Non-Match Test: [{0}] should not be recognized as an octal number")
        @ValueSource(strings = {
                "0",        // Single zero (potentially debatable)
                "08",       // Invalid octal digit
                "09",       // Invalid octal digit
                "a",        // Letter
                "0x123",    // Hex number
                "123",      // Decimal number
                " 01"       // Whitespace
        })
        void shouldNotMatchOctalNumber(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }

    @Nested
    class DecimalFloatNumberTest {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.decimalFloatNumber());

        /**
         * Test cases for decimalFloatNumber() method
         *
         * @param input Decimal float number to test
         */
        @ParameterizedTest(name = "Decimal Float Number Match Test: [{0}] should be recognized as a decimal float")
        @ValueSource(strings = {
                "123.456",      // Standard decimal with fraction
                ".456",         // Decimal starting with dot
                "123.",         // Decimal ending with dot
                "123e10",       // Decimal with exponent
                "123E-10",      // Decimal with negative exponent
                "123e+10",      // Decimal with positive exponent
                "123.456e10",   // Decimal with fraction and exponent
                "123f",         // Float suffix (lowercase)
                "123F",         // Float suffix (uppercase)
                "123.456d",     // Double suffix (lowercase)
                "123.456D",     // Double suffix (uppercase)
                "123.456e10f",  // Combination of exponent and suffix
                "0.0",          // Zero with fraction
                ".0"            // Zero starting with dot
        })
        void shouldMatchDecimalFloatNumber(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for decimalFloatNumber() method that should not match
         *
         * @param input Invalid decimal float number to test
         */
        @ParameterizedTest(name = "Decimal Float Number Non-Match Test: [{0}] should not be recognized as a decimal float")
        @ValueSource(strings = {
                "",             // Empty string
                "123",          // Integer without fraction or exponent
                "123e",         // Incomplete exponent
                "123e+",        // Incomplete exponent with sign
                "e10",          // Exponent without base
                ".e10",         // Dot with exponent but no digits
                "123a",         // Invalid suffix
                "0x123",        // Hex number
                " 123.456"      // Leading whitespace
        })
        void shouldNotMatchDecimalFloatNumber(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }

    @Nested
    class HexFloatNumberTest {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.hexFloatNumber());

        /**
         * Test cases for hexFloatNumber() method
         * @param input Hex float number to test
         */
        @ParameterizedTest(name = "Hex Float Number Match Test: [{0}] should be recognized as a hex float")
        @ValueSource(strings = {
                "0x123.456p10",     // Hex float with fraction and binary exponent
                "0X123p-10",        // Hex float with negative binary exponent
                "0x123p+10",        // Hex float with positive binary exponent
                "0x123.456p10f",    // Hex float with fraction, exponent, and float suffix
                "0x123.456P10D",    // Hex float with fraction, exponent, and double suffix
                "0x123p10",         // Hex float without fraction
                "0x123.p10",        // Hex float with empty fraction part
                "0x.456p10"         // Hex float starting with dot
        })
        void shouldMatchHexFloatNumber(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for hexFloatNumber() method that should not match
         * @param input Invalid hex float number to test
         */
        @ParameterizedTest(name = "Hex Float Number Non-Match Test: [{0}] should not be recognized as a hex float")
        @ValueSource(strings = {
                "",             // Empty string
                "0x123",        // Hex number without binary exponent
                "0x123p",       // Incomplete binary exponent
                "0x123p+",      // Incomplete binary exponent with sign
                "0x123.456.789p10", // Multiple decimal points
                "p10",          // Binary exponent without base
                "0x.p10",       // Dot with binary exponent but no digits
                "0x123a",       // Invalid suffix
                "123.456",      // Decimal float
                " 0x123p10"     // Leading whitespace
        })
        void shouldNotMatchHexFloatNumber(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }
}
