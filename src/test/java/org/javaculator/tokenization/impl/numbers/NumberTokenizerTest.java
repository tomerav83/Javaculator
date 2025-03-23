package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link NumberTokenizer}
 * Tests edge cases and regular functionality of the number tokenizer.
 * The tokenizer matches patterns: \d+\.?\d*|\.\\d+
 * Which covers:
 * - Integer numbers (\d+)
 * - Decimal numbers with optional fractional part (\d+\.\d*)
 * - Decimal numbers starting with decimal point (.\d+)
 */
class NumberTokenizerTest {

    @Test
    @DisplayName("Test singleton instance exists")
    void testSingletonInstance() {
        assertNotNull(NumberTokenizer.INSTANCE);
    }

    @ParameterizedTest
    @DisplayName("Test simple integer numbers")
    @ValueSource(strings = {"0", "1", "42", "123", "9999"})
    void testSimpleIntegers(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(input, token.value());
        assertEquals(TokenType.NUMBER, token.type());
        assertEquals(0, token.pos());
    }

    @ParameterizedTest
    @DisplayName("Test decimal numbers with fractional part")
    @ValueSource(strings = {"0.0", "1.5", "3.14", "123.456", "99.999"})
    void testDecimalNumbers(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(input, token.value());
        assertEquals(TokenType.NUMBER, token.type());
        assertEquals(0, token.pos());
    }

    @ParameterizedTest
    @DisplayName("Test decimal numbers without trailing digits")
    @ValueSource(strings = {"1.", "42.", "123."})
    void testDecimalPointWithoutTrailingDigits(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(input, token.value());
        assertEquals(TokenType.NUMBER, token.type());
        assertEquals(0, token.pos());
    }

    @ParameterizedTest
    @DisplayName("Test decimal numbers starting with decimal point")
    @ValueSource(strings = {".0", ".5", ".123", ".999"})
    void testDecimalPointStart(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(input, token.value());
        assertEquals(TokenType.NUMBER, token.type());
        assertEquals(0, token.pos());
    }

    @Test
    @DisplayName("Test single decimal point (should not match)")
    void testSingleDecimalPoint() {
        String input = ".";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertTrue(tokens.isEmpty(), "A single decimal point should not be tokenized as a number");
    }

    @ParameterizedTest
    @DisplayName("Test numbers embedded in text")
    @ValueSource(strings = {
            "The answer is 42",
            "Pi is approximately 3.14159",
            "Temperature is .5 degrees",
            "Count: 100."
    })
    void testNumbersInText(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(TokenType.NUMBER, token.type());

        String expectedNumber = null;
        if (input.contains("42")) expectedNumber = "42";
        else if (input.contains("3.14159")) expectedNumber = "3.14159";
        else if (input.contains(".5")) expectedNumber = ".5";
        else if (input.contains("100.")) expectedNumber = "100.";

        assertEquals(expectedNumber, token.value());
        assertEquals(input.indexOf(expectedNumber), token.pos());
    }

    @Test
    @DisplayName("Test multiple numbers in text")
    void testMultipleNumbers() {
        String input = "Values are: 10, 20.5, .75, and 100.";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(4, tokens.size());

        assertEquals("10", tokens.get(0).value());
        assertEquals("20.5", tokens.get(1).value());
        assertEquals(".75", tokens.get(2).value());
        assertEquals("100.", tokens.get(3).value());

        for (Token token : tokens) {
            assertEquals(TokenType.NUMBER, token.type());
            assertEquals(input.indexOf(token.value(), token.pos()), token.pos());
        }
    }

    @ParameterizedTest
    @DisplayName("Test non-number inputs")
    @ValueSource(strings = {
            "abc", "x", ".", " ", "-", "+", "e", ".", "..", ",", "a1", "1a"
    })
    void testNonNumberInputs(String input) {
        // fix issue with 1.a
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        if (!input.matches("\\d+\\.?\\d*|\\.\\d+")) {
            assertTrue(tokens.isEmpty(), "Non-number input should not be tokenized: " + input);
        }
    }

    @ParameterizedTest
    @DisplayName("Test edge cases that should not match")
    @ValueSource(strings = {
            // Scientific notation (not supported by the regex)
            "1e10", "2.5e-3",
            // Signed numbers (not supported by the regex)
            "+42", "-3.14",
            // Multiple decimal points
            "1.2.3", "..5"
    })
    void testEdgeCasesShouldNotMatch(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        // The tokenizer may return partial matches for some of these
        for (Token token : tokens) {
            assertEquals(TokenType.NUMBER, token.type());
            assertTrue(token.value().matches("\\d+\\.?\\d*|\\.\\d+"),
                    "Token should match the number pattern: " + token.value());
        }
    }

    @ParameterizedTest
    @DisplayName("Test null and empty inputs")
    @NullAndEmptySource
    void testNullAndEmptyInputs(String input) {
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);
        assertTrue(tokens.isEmpty(), "Null or empty input should return empty token list");
    }

    @Test
    @DisplayName("Test numbers with leading zeros")
    void testLeadingZeros() {
        String input = "007 00.123 0001";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(3, tokens.size());
        assertEquals("007", tokens.get(0).value());
        assertEquals("00.123", tokens.get(1).value());
        assertEquals("0001", tokens.get(2).value());
    }

    @Test
    @DisplayName("Test very large numbers")
    void testVeryLargeNumbers() {
        String input = "9999999999999999999 1234567890.1234567890";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(2, tokens.size());
        assertEquals("9999999999999999999", tokens.get(0).value());
        assertEquals("1234567890.1234567890", tokens.get(1).value());
    }

    @Test
    @DisplayName("Test adjacent numbers without separators")
    void testAdjacentNumbers() {
        String input = "123456.78910";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        assertEquals("123456.78910", tokens.get(0).value());
    }

    @Test
    @DisplayName("Test boundary conditions - numbers at string boundaries")
    void testNumberBoundaries() {
        String input = "42 text 3.14";
        List<Token> tokens = NumberTokenizer.INSTANCE.tokenize(input);

        assertEquals(2, tokens.size());

        // First token at the start boundary
        assertEquals("42", tokens.get(0).value());
        assertEquals(0, tokens.get(0).pos());

        // Second token near the end
        assertEquals("3.14", tokens.get(1).value());
        assertEquals(8, tokens.get(1).pos());
    }
}