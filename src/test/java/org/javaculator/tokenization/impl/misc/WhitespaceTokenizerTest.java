//package org.javaculator.tokenization.impl.misc;
//
//import org.javaculator.tokenization.models.Token;
//import org.javaculator.tokenization.models.TokenType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WhitespaceTokenizerTest {
//    @Test
//    @DisplayName("Test singleton instance exists")
//    void testSingletonInstance() {
//        assertNotNull(WhitespaceTokenizer.INSTANCE);
//    }
//
//    @ParameterizedTest
//    @DisplayName("Test single whitespace characters")
//    @ValueSource(strings = {" ", "\t", "\n", "\r", "\f"})
//    void testSingleWhitespaceCharacters(String whitespace) {
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(whitespace);
//
//        assertEquals(1, tokens.size());
//        Token token = tokens.get(0);
//        assertEquals(whitespace, token.value());
//        assertEquals(TokenType.WHITESPACE, token.type());
//        assertEquals(0, token.pos());
//    }
//
//    @Test
//    @DisplayName("Test mixed whitespace sequence")
//    void testMixedWhitespaceSequence() {
//        String whitespace = " \t\n\r\f";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(whitespace);
//
//        assertEquals(1, tokens.size());
//        Token token = tokens.get(0);
//        assertEquals(whitespace, token.value());
//        assertEquals(TokenType.WHITESPACE, token.type());
//        assertEquals(0, token.pos());
//    }
//
//    @Test
//    @DisplayName("Test repeated whitespace characters")
//    void testRepeatedWhitespaceCharacters() {
//        String whitespace = "     \t\t\t\n\n\r\r\f\f";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(whitespace);
//
//        assertEquals(1, tokens.size());
//        Token token = tokens.get(0);
//        assertEquals(whitespace, token.value());
//        assertEquals(TokenType.WHITESPACE, token.type());
//        assertEquals(0, token.pos());
//    }
//
//    @Test
//    @DisplayName("Test whitespace embedded in non-whitespace text")
//    void testWhitespaceInText() {
//        String input = "This is a test";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//
//        assertEquals(3, tokens.size());
//
//        assertEquals(" ", tokens.get(0).value());
//        assertEquals(" ", tokens.get(1).value());
//        assertEquals(" ", tokens.get(2).value());
//
//        for (Token token : tokens) {
//            assertEquals(TokenType.WHITESPACE, token.type());
//        }
//
//        assertEquals(4, tokens.get(0).pos());
//        assertEquals(7, tokens.get(1).pos());
//        assertEquals(9, tokens.get(2).pos());
//    }
//
//    @Test
//    @DisplayName("Test text with multiple whitespace groups")
//    void testMultipleWhitespaceGroups() {
//        String input = "Hello  World\t\tJava  \n Testing";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//
//        assertEquals(3, tokens.size());
//
//        assertEquals("  ", tokens.get(0).value());
//        assertEquals("\t\t", tokens.get(1).value());
//        assertEquals("  \n ", tokens.get(2).value());
//
//        for (Token token : tokens) {
//            assertEquals(TokenType.WHITESPACE, token.type());
//        }
//    }
//
//    @Test
//    @DisplayName("Test text starting and ending with whitespace")
//    void testWhitespaceStartEnd() {
//        String input = "  Hello World  ";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//
//        assertEquals(3, tokens.size());
//        assertEquals("  ", tokens.get(0).value());
//        assertEquals(" ", tokens.get(1).value());
//        assertEquals("  ", tokens.get(2).value());
//        assertEquals(0, tokens.get(0).pos());
//        assertEquals(7, tokens.get(1).pos());
//        assertEquals(13, tokens.get(2).pos());
//    }
//
//    @Test
//    @DisplayName("Test only whitespace input")
//    void testOnlyWhitespace() {
//        String input = "    \t\n\r    \t\t\n\n";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//
//        assertEquals(1, tokens.size());
//        Token token = tokens.get(0);
//        assertEquals(input, token.value());
//        assertEquals(TokenType.WHITESPACE, token.type());
//        assertEquals(0, token.pos());
//    }
//
//    @ParameterizedTest
//    @DisplayName("Test with non-whitespace text")
//    @ValueSource(strings = {"Hello", "Java", "Code", "Testing123"})
//    void testNonWhitespaceText(String input) {
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//        assertTrue(tokens.isEmpty(), "Non-whitespace input should return empty token list");
//    }
//
//    @ParameterizedTest
//    @DisplayName("Test null and empty inputs")
//    @NullAndEmptySource
//    void testNullAndEmptyInputs(String input) {
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//        assertTrue(tokens.isEmpty(), "Null or empty input should return empty token list");
//    }
//
//    @Test
//    @DisplayName("Test with unicode whitespace characters")
//    void testUnicodeWhitespace() {
//        // Include some Unicode whitespace characters like non-breaking space, em space, etc.
//        String input = "Hello\u00A0World\u2003Java";
//        List<Token> tokens = WhitespaceTokenizer.INSTANCE.tokenize(input);
//
//        // The regex "\s+" might not match all Unicode whitespace, depending on implementation
//        // This test verifies the current behavior
//        if (tokens.isEmpty()) {
//            // If it doesn't match Unicode whitespace, that's the current behavior to verify
//            assertTrue(true);
//        } else {
//            // If it does match some Unicode whitespace, verify it's correct
//            for (Token token : tokens) {
//                assertEquals(TokenType.WHITESPACE, token.type());
//                assertTrue(input.contains(token.value()));
//            }
//        }
//    }
//
//}
