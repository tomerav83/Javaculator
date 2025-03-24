package org.javaculator.tokenization.impl.identifiers;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class VariableTokenizerTest {
    @ParameterizedTest(name = "Should match single character identifiers: {0}")
    @ValueSource(strings = {
            "a", "z", "A", "Z", "_"
    })
    void shouldMatchSingleCharacterIdentifiers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match lowercase letter identifiers: {0}")
    @ValueSource(strings = {
            "abc", "foo", "bar", "hello", "world", "identifier"
    })
    void shouldMatchLowercaseIdentifiers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match uppercase letter identifiers: {0}")
    @ValueSource(strings = {
            "ABC", "FOO", "BAR", "HELLO", "WORLD", "IDENTIFIER"
    })
    void shouldMatchUppercaseIdentifiers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match mixed case letter identifiers: {0}")
    @ValueSource(strings = {
            "camelCase", "PascalCase", "MixedCASE", "halfUPPER", "halfLower"
    })
    void shouldMatchMixedCaseIdentifiers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match identifiers with numbers: {0}")
    @ValueSource(strings = {
            "a1", "z9", "foo123", "bar456", "id42", "var01", "temp2024"
    })
    void shouldMatchIdentifiersWithNumbers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match identifiers with underscores: {0}")
    @ValueSource(strings = {
            "_", "_a", "_A", "_1", "a_b", "foo_bar", "hello_world", "CONST_VALUE",
            "snake_case", "UPPER_SNAKE_CASE", "___"
    })
    void shouldMatchIdentifiersWithUnderscores(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match complex identifiers: {0}")
    @ValueSource(strings = {
            "a1_b2", "foo_123_bar", "THE_ANSWER_42", "temp_var_2024", "x_1_y_2_z_3",
            "_complex_ID_with_123_and_MIXED_case"
    })
    void shouldMatchComplexIdentifiers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }


    @ParameterizedTest(name = "Should not match empty or null input: {0}")
    @NullAndEmptySource
    void shouldNotMatchEmptyInput(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match identifiers starting with numbers: {0}")
    @ValueSource(strings = {
            "0", "1", "2", "9", "123", "42abc", "9foo", "0_bar", "1_"
    })
    void shouldNotMatchIdentifiersStartingWithNumbers(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match identifiers with special characters: {0}")
    @ValueSource(strings = {
            "a-b", "foo+bar", "hello.world", "var@temp", "x$y", "name#tag", "first&second",
            "less<greater>", "open(close)", "curly{brace}", "square[bracket]", "percent%sign",
            "caret^symbol", "tilde~character", "pipe|symbol", "back\\slash", "forward/slash"
    })
    void shouldNotMatchIdentifiersWithSpecialCharacters(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match identifiers with spaces: {0}")
    @ValueSource(strings = {
            " ", "a b", "foo bar", " leading", "trailing ", " both ends ", "multi  space"
    })
    void shouldNotMatchIdentifiersWithSpaces(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match identifiers with non-ASCII characters: {0}")
    @ValueSource(strings = {
            "café", "nöel", "résumé", "piñata", "über", "naïve", "日本語", "中文"
    })
    void shouldNotMatchIdentifiersWithNonAsciiCharacters(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match identifiers with control characters: {0}")
    @ValueSource(strings = {
            "abc\ndef", "tab\t", "new\nline", "carriage\rreturn"
    })
    void shouldNotMatchIdentifiersWithControlCharacters(String input) {
        Truth.assertThat(VariableTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }
}
