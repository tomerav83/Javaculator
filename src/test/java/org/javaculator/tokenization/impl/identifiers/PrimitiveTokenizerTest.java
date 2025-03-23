package org.javaculator.tokenization.impl.identifiers;

import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrimitiveTokenizerTest {
    @Test
    @DisplayName("Test singleton instance exists")
    void testSingletonInstance() {
        assertNotNull(PrimitiveTypeTokenizer.INSTANCE);
    }

    @ParameterizedTest
    @DisplayName("Test all valid Java primitive types are tokenized correctly")
    @ValueSource(strings = {"byte", "short", "int", "long", "float", "double", "boolean", "char"})
    void testValidPrimitiveTypes(String primitiveType) {
        List<Token> tokens = PrimitiveTypeTokenizer.INSTANCE.tokenize(primitiveType);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        assertEquals(primitiveType, token.value());
        assertEquals(TokenType.PRIMITIVE, token.type());
        assertEquals(0, token.pos());
    }

    @ParameterizedTest
    @DisplayName("Test primitive types with extra whitespace")
    @ValueSource(strings = {"  byte  ", " int ", "\tboolean\t", "\nchar\n"})
    void testPrimitiveTypesWithWhitespace(String input) {
        List<Token> tokens = PrimitiveTypeTokenizer.INSTANCE.tokenize(input);

        assertEquals(1, tokens.size());
        Token token = tokens.get(0);
        String expectedType = input.trim();
        assertEquals(expectedType, token.value());
        assertEquals(TokenType.PRIMITIVE, token.type());
        assertEquals(input.indexOf(expectedType), token.pos());
    }

    @ParameterizedTest
    @DisplayName("Test primitive type-like words that should not match")
    @ValueSource(strings = {
            "bytes", "shorts", "integer", "longValue", "floating", "doubleClick", "booleanValue", "character",
            "myint", "intValue", "abyte", "aboutboolean", "Byte", "Short", "Integer", "Long", "Float", "Double", "Boolean", "Character"
    })
    void testPrimitiveTypeLikeWords(String input) {
        List<Token> tokens = PrimitiveTypeTokenizer.INSTANCE.tokenize(input);
        assertTrue(tokens.isEmpty(), "Non-primitive type words should not be tokenized: " + input);
    }

    @ParameterizedTest
    @DisplayName("Test null and empty inputs")
    @NullAndEmptySource
    void testNullAndEmptyInputs(String input) {
        List<Token> tokens = PrimitiveTypeTokenizer.INSTANCE.tokenize(input);
        assertTrue(tokens.isEmpty(), "Null or empty input should return empty token list");
    }

    @Test
    @DisplayName("Test primitive types with case sensitivity")
    void testCaseSensitivity() {
        List<Token> tokens = PrimitiveTypeTokenizer.INSTANCE.tokenize("Int int INT");

        assertEquals(1, tokens.size());
        assertEquals("int", tokens.get(0).value());
    }


}
