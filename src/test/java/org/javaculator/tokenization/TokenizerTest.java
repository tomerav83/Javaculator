package org.javaculator.tokenization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TokenizerTest {
    @Nested
    class NumberTokenizationTests {
        @ParameterizedTest(name = "Number: {0}")
        @ValueSource(strings = {"0", "42", "9876543210", "0.0", "3.14159", ".14159", "3."})
        void shouldMatchNumbers(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.NUMBER, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }

        @ParameterizedTest(name = "Number: {0}")
        @ValueSource(strings = {" 0", " 42", " 9876543210", " 0.0", " 3.14159", " .14159", " 3."})
        void shouldMatchWhitespacePrefixNumbers(String input) {
            Token token = Tokenizer.tokenize(input).get(1);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, token.pos()),
                    () -> Assertions.assertEquals(TokenType.NUMBER, token.type()),
                    () -> Assertions.assertEquals(input.trim(), token.value())
            );
        }

        @ParameterizedTest(name = "Number: {0}")
        @ValueSource(strings = {"0 ", "42 ", "9876543210 ", "0.0 ", "3.14159 ", ".14159 ", "3. "})
        void shouldMatchWhitespaceSuffixNumbers(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.NUMBER, token.type()),
                    () -> Assertions.assertEquals(input.trim(), token.value())
            );
        }
    }

    @Nested
    class IdentifierTokenizationTests {
        @ParameterizedTest(name = "Identifier: {0}")
        @ValueSource(strings = {"a", "A", "word", "_underscore", "prefix_suffix", "camelCase"})
        void shouldMatchIdentifiers(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.IDENTIFIER, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }

        @ParameterizedTest(name = "Keyword: {0}")
        @ValueSource(strings = {"var"})
        void shouldMatchKeywords(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.KEYWORD, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }
    }

    @Nested
    class StringTokenizationTests {
        // see if need to tokenize strings
    }

    @Nested
    class OperatorTokenizationTests {

        @ParameterizedTest(name = "Operator: {0}")
        @ValueSource(strings = {"=", "+", "-", "*", "/", "%", "==", "!=", "<=", ">=", "++", "--"})
        void shouldMatchBasicOperators(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.OPERATOR, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }
    }

    @Nested
    class PunctuationTokenizationTests {
        @ParameterizedTest(name = "Punctuation: {0}")
        @ValueSource(strings = {";", "(", ")", ",", "{", "}"})
        void shouldMatchPunctuation(String input) {
            Token token = Tokenizer.tokenize(input).get(0);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(0, token.pos()),
                    () -> Assertions.assertEquals(TokenType.PUNCTUATION, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }
    }
}
