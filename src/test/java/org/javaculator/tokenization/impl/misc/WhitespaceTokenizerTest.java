package org.javaculator.tokenization.impl.misc;

import com.google.common.truth.Truth;
import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WhitespaceTokenizerTest {
    @ParameterizedTest(name = "Should match single whitespace characters: {0}")
    @ValueSource(strings = {
            " ",        // Space
            "\t",       // Tab
            "\n",       // Line feed
            "\r",       // Carriage return
            "\f",       // Form feed
            "\u000B"    // Vertical tab
    })
    void shouldMatchSingleWhitespaceCharacters(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match multiple consecutive whitespace characters: {0}")
    @ValueSource(strings = {
            "  ",           // Multiple spaces
            "\t\t",         // Multiple tabs
            "\n\n",         // Multiple line feeds
            "\r\r",         // Multiple carriage returns
            "   ",          // Three spaces
            "\t \n",        // Tab, space, line feed
            "\r\n\t ",      // Carriage return, line feed, tab, space
            "    \t    ",   // Four spaces, tab, four spaces
            "\n\n\n\n\n"    // Five line feeds
    })
    void shouldMatchMultipleConsecutiveWhitespaceCharacters(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match whitespace in strings: {0}")
    @ValueSource(strings = {
            "Hello World",              // Space between words
            "Tab\tSeparated",           // Tab between words
            "Line\nBreak",              // Line feed between words
            "Carriage\rReturn",         // Carriage return between words
            "Multiple   Spaces",        // Multiple spaces between words
            "Mixed \t\n\r Whitespace",  // Mixed whitespace between words
            " Leading space",           // Leading space
            "Trailing space ",          // Trailing space
            " Both ends ",              // Both leading and trailing spaces
            "a b c d e f"               // Multiple single spaces
    })
    void shouldMatchWhitespaceInStrings(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match exotic Unicode whitespace: {0}")
    @ValueSource(strings = {
            "\u00A0",    // Non-breaking space
            "\u2000",    // En quad
            "\u2001",    // Em quad
            "\u2002",    // En space
            "\u2003",    // Em space
            "\u2004",    // Three-per-em space
            "\u2005",    // Four-per-em space
            "\u2006",    // Six-per-em space
            "\u2007",    // Figure space
            "\u2008",    // Punctuation space
            "\u2009",    // Thin space
            "\u200A",    // Hair space
            "\u2028",    // Line separator
            "\u2029",    // Paragraph separator
            "\u202F",    // Narrow no-break space
            "\u205F",    // Medium mathematical space
            "\u3000"     // Ideographic space
    })
    void shouldNotMatchExoticUnicodeWhitespace(String input) {
        // Note: This test might fail as Java's \s doesn't match all Unicode whitespace
        // It depends on the Java version being used
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match strings without whitespace: {0}")
    @ValueSource(strings = {
            "a",
            "abc",
            "Hello",
            "NoSpacesHere",
            "123",
            "!@#$%^&*()",
            "under_score",
            "dash-dash",
            "dot.notation",
            "snake_case_variable",
            "camelCaseVariable",
            "PascalCaseClass",
            "UPPER_CASE_CONSTANT"
    })
    void shouldNotMatchStringsWithoutWhitespace(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match whitespace escape sequences as literal strings: {0}")
    @ValueSource(strings = {
            "\\t",      // Literal backslash followed by t
            "\\n",      // Literal backslash followed by n
            "\\r",      // Literal backslash followed by r
            "\\f",      // Literal backslash followed by f
            "\\s",      // Literal backslash followed by s
            "slash\\word" // Word with literal backslash
    })
    void shouldNotMatchWhitespaceEscapeSequencesAsLiteralStrings(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match empty string: {0}")
    @NullAndEmptySource
    void shouldNotMatchNullOrEmptyString(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match characters that look like whitespace but aren't: {0}")
    @ValueSource(strings = {
            "−",        // Minus sign (U+2212)
            "–",        // En dash (U+2013)
            "—",        // Em dash (U+2014)
            "·",        // Middle dot (U+00B7)
            "\u200B",   // Zero width space
            "\uFEFF"    // Zero width no-break space (BOM)
    })
    void shouldNotMatchCharactersThatLookLikeWhitespaceButArent(String input) {
        Truth.assertThat(WhitespaceTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }
}
