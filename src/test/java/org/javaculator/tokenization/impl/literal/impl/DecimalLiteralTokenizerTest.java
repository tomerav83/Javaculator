package org.javaculator.tokenization.impl.literal.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecimalLiteralTokenizerTest {

    private final DecimalLiteralTokenizer tokenizer = new DecimalLiteralTokenizer();

    // Valid integer literal: positive number.
    @Test
    void testValidPositiveInteger() {
        Assertions.assertEquals(123, tokenizer.tokenize("123"));
    }

    // Valid integer literal: negative number.
    @Test
    void testValidNegativeInteger() {
        Assertions.assertEquals(-456, tokenizer.tokenize("-456"));
    }

    // Valid integer literal: positive number with an explicit plus sign.
    @Test
    void testValidPositiveIntegerWithPlus() {
        Assertions.assertEquals(789, tokenizer.tokenize("+789"));
    }

    // Valid literal: the zero literal.
    @Test
    void testZeroLiteral() {
        // Although 0 is a valid literal, note that 0 is also the default
        // for non-matching tokens. Here we verify that "0" is parsed as 0.
        Assertions.assertEquals(0, tokenizer.tokenize("0"));
    }

    // Edge case: token is only a plus sign (should not match).
    @Test
    void testOnlyPlusSign() {
        Assertions.assertEquals(0, tokenizer.tokenize("+"));
    }

    // Edge case: token is only a minus sign (should not match).
    @Test
    void testOnlyMinusSign() {
        Assertions.assertEquals(0, tokenizer.tokenize("-"));
    }

    // Token contains non-numeric characters.
    @Test
    void testAlphabeticString() {
        Assertions.assertEquals(0, tokenizer.tokenize("abc"));
    }

    // Token is a mix of digits and letters.
    @Test
    void testAlphanumericString() {
        // Assuming the regex does not match if there are extra characters.
        Assertions.assertEquals(0, tokenizer.tokenize("123abc"));
    }

    // Token contains leading/trailing whitespace.
    @Test
    void testStringWithWhitespace() {
        // Whitespace likely prevents a match if the regex is anchored.
        Assertions.assertEquals(0, tokenizer.tokenize(" 123 "));
    }

    // Empty string should yield the default value.
    @Test
    void testEmptyString() {
        Assertions.assertEquals(0, tokenizer.tokenize(""));
    }

    // A string representing a floating-point number should not match.
    @Test
    void testDecimalWithPoint() {
        // If Patterns.DECIMAL_LITERAL only matches integer literals,
        // "123.0" should return 0 because it isn’t a valid integer literal.
        Assertions.assertEquals(0, tokenizer.tokenize("123.0"));
    }

    // Passing a null token should throw a NullPointerException.
    @Test
    void testNullToken() {
        Assertions.assertThrows(NullPointerException.class, () -> tokenizer.tokenize(null));
    }
}
