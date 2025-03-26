package org.javaculator.rules.numbers;

import org.javaculator.rules.Parsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.parboiled.parserunners.ReportingParseRunner;

public class DigitsTest {
    @Nested
    class BasicDigitTests {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.digit());

        /**
         * Test cases for digit() method
         *
         * @param input Character to test as a digit
         */
        @ParameterizedTest(name = "Digit Test: [{0}] should be recognized as a digit")
        @ValueSource(strings = {
                "0",    // Lower boundary
                "5",    // Mid-range digit
                "9"     // Upper boundary
        })
        void shouldMatchDigit(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for digit() method that should not match
         *
         * @param input Character to test as a non-digit
         */
        @ParameterizedTest(name = "Non-Digit Test: [{0}] should not be recognized as a digit")
        @ValueSource(strings = {
                "a",    // Lowercase letter
                "A",    // Uppercase letter
                " ",    // Whitespace
                "!",    // Punctuation
                "@",    // Special character
                "㉿"    // Unicode character
        })
        void shouldNotMatchNonDigit(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }

    @Nested
    class HexDigitTests {
        private final ReportingParseRunner<?> parser = new ReportingParseRunner<>(Parsers.hexDigit());

        /**
         * Test cases for hexDigit() method
         *
         * @param input Character to test as a hex digit
         */
        @ParameterizedTest(name = "Hex Digit Test: [{0}] should be recognized as a hex digit")
        @ValueSource(strings = {
                "0",    // Numeric digit
                "5",    // Mid-range numeric digit
                "9",    // Upper numeric digit
                "a",    // Lowercase hex letter
                "f",    // Lowercase upper hex letter
                "A",    // Uppercase hex letter
                "F"     // Uppercase upper hex letter
        })
        void shouldMatchHexDigit(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases for hexDigit() method that should not match
         *
         * @param input Character to test as a non-hex digit
         */
        @ParameterizedTest(name = "Non-Hex Digit Test: [{0}] should not be recognized as a hex digit")
        @ValueSource(strings = {
                "g",    // First letter outside hex range
                "z",    // Lowercase letter outside hex range
                "G",    // Uppercase letter outside hex range
                "Z",    // Uppercase letter outside hex range
                " ",    // Whitespace
                "!",    // Punctuation
                "@",    // Special character
                "㉿"    // Unicode character
        })
        void shouldNotMatchNonHexDigit(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }
}
