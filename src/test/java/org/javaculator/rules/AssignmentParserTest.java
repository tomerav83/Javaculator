package org.javaculator.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.parboiled.parserunners.ReportingParseRunner;

public class AssignmentParserTest {

    @Nested
    class WhitespaceRuleTests {
        private final ReportingParseRunner<?> parser =
                new ReportingParseRunner<>(Parsers.whitespace());

        /**
         * Test cases that should match whitespace characters
         * @param input Character to be tested for whitespace matching
         */
        @ParameterizedTest(name = "Whitespace Match Test: [{0}] should be recognized as whitespace")
        @ValueSource(strings = {
                " ",    // Space
                "\t",   // Tab
                "\f",   // Form feed
                "\n",   // Newline
                "\r"    // Carriage return
        })
        void shouldMatchWhitespaceCharacters(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases that should match whitespace characters, as we use ZeroOrMore rule
         * @param input Character to be tested for non-whitespace
         */
        @ParameterizedTest(name = "Whitespace Match Test: [{0}] should be recognized as whitespace because of ZeroOrMore")
        @ValueSource(strings = {
                "a",    // Lowercase letter
                "Z",    // Uppercase letter
                "0",    // Digit
                "!",    // Punctuation
                "@",    // Special character
                "㉿",   // Unicode character
                "\u0000" // Null character
        })
        void shouldNotMatchWhitespaceCharacters(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Additional parameterized test for edge case scenarios
         * @param input String with mixed whitespace and non-whitespace
         */
        @ParameterizedTest(name = "Complex Whitespace Test: [{0}] with mixed content")
        @ValueSource(strings = {
                " abc",
                "\tabc",
                "\f123",
                "\n def",
                "\r xyz",
                " \t\f\n\r"
        })
        void shouldHandleComplexWhitespaceScenarios(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }
    }

    @Nested
    class DigitsRuleTests {
        private final ReportingParseRunner<?> parser =
                new ReportingParseRunner<>(Parsers.digit());

        /**
         * Test cases that should match single digit characters
         * @param input Character to be tested for digit matching
         */
        @ParameterizedTest(name = "Strict Digit Match Test: [{0}] should be recognized as a single ")
        @ValueSource(strings = {
                "0",    // Lower boundary
                "5",    // Mid-range digit
                "9",    // Upper boundary
                "1",    // Lower range digit
                "8"    // Upper range digit
        })
        void shouldMatchSingleDigitCharacters(String input) {
            Assertions.assertTrue(parser.run(input).matched);
        }

        /**
         * Test cases that should not match
         * @param input Input to be tested for non-digit matching
         */
        @ParameterizedTest(name = "Strict Digit Non-Match Test: [{0}] should not be recognized as a digit")
        @ValueSource(strings = {
                "123",      // Multiple digits
                "a1b2c3",   // Multiple characters with digits
                "12.23",    // Full floating point
                ".23",      // Bottom floating point
                "12.",      // Top floating point
                "abc9",     // Digit with letters
                " 5 ",      // Digit with whitespace
                "a",        // Lowercase letter
                "Z",        // Uppercase letter
                " ",        // Space
                "\t",       // Tab
                "!",        // Punctuation
                "@",        // Special character
                "㉿",       // Unicode character
                "\u0000",   // Null character
                "/",        // Just before '0'
                ":",        // Just after '9'
                "-"         // Another character before range
        })
        void shouldNotMatchNonSingleDigitCharacters(String input) {
            Assertions.assertFalse(parser.run(input).matched);
        }
    }
}
