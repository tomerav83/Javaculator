package org.javaculator.tokenization.impl.numbers;

import com.google.common.truth.Truth;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BinaryTokenizerTest {
    @ParameterizedTest(name = "Should match single digit binary value: {0}")
    @ValueSource(strings = {
            "0b0", "0B0", "0b1", "0B1"
    })
    void shouldMatchBasicSingleDigitBinary(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match multi-digit binary value: {0}")
    @ValueSource(strings = {
            "0b10", "0B10", "0b01", "0B01",
            "0b101", "0B101", "0b010", "0B010",
            "0b11111111", "0B11111111", "0b00000000", "0B00000000"
    })
    void shouldMatchMultiDigitBinary(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match binary with long suffix: {0}")
    @ValueSource(strings = {
            "0b1L", "0B1l", "0b0L", "0B0l",
            "0b101L", "0B101l", "0b010L", "0B010l",
            "0b11111111L", "0B11111111l"
    })
    void shouldMatchBinaryWithLongSuffix(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match binary with underscores: {0}")
    @ValueSource(strings = {
            "0b1_0", "0B1_0", "0b0_1", "0B0_1",
            "0b1_0_1", "0B1_0_1", "0b0_1_0", "0B0_1_0",
            "0b1_0_1_0_1", "0B1_0_1_0_1",
            "0b11_11_11_11", "0B00_00_00_00"
    })
    void shouldMatchBinaryWithUnderscores(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match binary with underscores and long suffix: {0}")
    @ValueSource(strings = {
            "0b1_0L", "0B1_0l", "0b0_1L", "0B0_1l",
            "0b1_0_1L", "0B1_0_1l", "0b1_1_1_1_1L", "0B0_0_0_0_0l"
    })
    void shouldMatchBinaryWithUnderscoresAndLongSuffix(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match binary with non-alphanumeric context: {0}")
    @ValueSource(strings = {
            " 0b1 ", "\t0B10\n", "foo 0b101 bar",
            "(0b10)", "[0B101]", "-0b1-", "+0B101+",
            ";0b10;", ",0B101,", ".0b1."
    })
    void shouldMatchBinaryWithNonAlphanumericContext(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match binary edge cases: {0}")
    @ValueSource(strings = {
            "0b0", "0B0", // Zero value
            "0b1", "0B1", // One value
            "0b11111111", "0B11111111", // 8-bit max value (255)
            "0b10000000", "0B10000000", // 128
            "0b01111111", "0B01111111", // 127
            "0b11111111111111111111111111111111L", // 32-bit max value
            "0B11111111111111111111111111111111l", // 32-bit max value
            "0b10000000000000000000000000000000L", // Min negative 32-bit
            "0B10000000000000000000000000000000l", // Min negative 32-bit
            "0b1111111111111111111111111111111111111111111111111111111111111111L", // 64-bit max
            "0B1111111111111111111111111111111111111111111111111111111111111111l"  // 64-bit max
    })
    void shouldMatchBinaryEdgeCases(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should not match invalid binary format: {0}")
    @ValueSource(strings = {
            "0b", "0B", // Missing at least one binary digit
            "0b2", "0B9", "0bA", "0BF", // Invalid binary characters
            "0b_1", "0B_0", // Underscore at start of digits
            "0b1_", "0B0_", // Underscore at end of digits
            "0bLL", "0BlL", // Multiple L suffixes
            "0b101LL", "0B010lL" // Multiple L suffixes
    })
    void shouldNotMatchInvalidBinaryFormat(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match binary with invalid context: {0}")
    @ValueSource(strings = {
            "a0b1", "A0B1", // Preceded by letter
            "_0b1", "_0B1", // Preceded by underscore
            "10b1", "20B1", // Preceded by digit
            "0b1a", "0B1A", // Followed by letter
            "0b1_", "0B1_", // Followed by underscore (already in invalid formats)
            "a0b1a", "A0B1A", // Surrounded by letters
            "word0b1word", "WORD0B101WORD", // Part of words
            "var_0b1_val", "VAR_0B101_VAL", // Part of variables
            "bin0b101", "BIN0B101" // Part of identifiers
    })
    void shouldNotMatchWithInvalidContext(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    

    @ParameterizedTest(name = "Should not match invalid binary edge cases: {0}")
    @ValueSource(strings = {
            "0bL", "0Bl", // Missing digit before L
            "b101", "B101", // Missing leading 0
            "0c101", "0D101", // Wrong prefix character
            "0b_", "0B_" // Underscore with no digits
    })
    void shouldNotMatchInvalidBinaryEdgeCases(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match other number types: {0}")
    @ValueSource(strings = {
            "0x123", "0X123", // Hexadecimal literals
            "0123", "0567", // Octal literals
            "123", "456", // Decimal literals
            "123.456", "0.123", // Floating point
            "123e45", "123E+45", // Scientific notation
            "123f", "123F", "123d", "123D" // Float/Double suffixes
    })
    void shouldNotMatchOtherNumberTypes(String input) {
        Truth.assertThat(BinaryTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }
}
