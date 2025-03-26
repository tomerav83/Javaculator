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
                new ReportingParseRunner<>(AssignmentParser.create().whitespace());

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
}
