package org.javaculator.tokenization.impl.identifiers;

import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTokenizerTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "identifier",
            "Identifier",
            "_identifier",
            "a",
            "Z",
            "_",
            "a1",
            "a_1",
            "_1",
            "identifier123",
            "CONSTANT_VALUE",
            "camelCaseIdentifier",
            "PascalCaseIdentifier",
            "snake_case_identifier",
            "SCREAMING_SNAKE_CASE",
            "Identifier_With_123_Numbers",
            "_____",
            "a_very_long_identifier_with_many_underscores_and_numbers_123456789"
    })
    void testValidIdentifiers(String identifier) {
        Token token = VariableTokenizer.INSTANCE.tokenize(identifier).get(0);

        assertNotNull(token);
        assertEquals(TokenType.VARIABLE, token.type());
        assertEquals(identifier, token.value());
        assertEquals(0, token.pos());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1identifier",        // Starts with a digit
            "123",                // All digits
            "identifier-name",    // Contains hyphen
            "identifier.name",    // Contains period
            "identifier name",    // Contains space
            "$identifier",        // Starts with dollar sign (valid in Java but not in the regex)
            "identifier#suffix",  // Contains special character
            "identifier+name",    // Contains operator
            "\"identifier\"",     // Contains quotes
            "if",                 // Reserved keyword (would be valid but typically handled separately)
            "for"                 // Reserved keyword (would be valid but typically handled separately)
    })
    void testInvalidIdentifiers(String input) {
        assertNotEquals(1, VariableTokenizer.INSTANCE.tokenize(input).size());
    }
}
