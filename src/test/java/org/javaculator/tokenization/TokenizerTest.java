package org.javaculator.tokenization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenizerTest {
    @Nested
    class NumberTokenizationTests {
        @ParameterizedTest(name = "Integer: {0}")
        @MethodSource("getIntegerScenarios")
        void shouldMatchIntegers(String input, int expectedPos, TokenType expectedType) {
            Token token = Tokenizer.tokenize(input).get(expectedPos);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(expectedPos, token.pos()),
                    () -> Assertions.assertEquals(expectedType, token.type()),
                    () -> Assertions.assertEquals(input, token.value())
            );
        }

        @ParameterizedTest(name = "Decimal: {0}")
        @MethodSource("getDecimalScenarios")
        void shouldMatchDecimals(String input, int expectedPos, TokenType expectedType) {
            Token token = Tokenizer.tokenize(input).get(expectedPos);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(expectedPos, token.pos()),
                    () -> Assertions.assertEquals(expectedType, token.type()),
                    () -> Assertions.assertEquals(input, token.value()));
        }

        private static Stream<Arguments> getIntegerScenarios() {
            return Stream.of(
                    Arguments.of("0", 0, TokenType.NUMBER),
                    Arguments.of("42", 0, TokenType.NUMBER),
                    Arguments.of("9876543210", 0, TokenType.NUMBER));
        }

        private static Stream<Arguments> getDecimalScenarios() {
            return Stream.of(
//                    Arguments.of("0.0", 0, TokenType.NUMBER),
//                    Arguments.of("3.14159", 0, TokenType.NUMBER),
                    Arguments.of(".14159", 0, TokenType.NUMBER),
                    Arguments.of("3.", 0, TokenType.NUMBER));
        }
    }
}
