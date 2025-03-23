//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import java.util.List;
//import java.util.regex.Pattern;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DisplayName("Assignment Operator Tokenizer Tests")
//class AssignmentOperatorTokenizerTest {
//
//    @DisplayName("Test singleton instance creation")
//    @Test
//    void testSingletonInstance() {
//        // When
//        AssignmentOperatorTokenizer instance1 = AssignmentOperatorTokenizer.INSTANCE;
//        AssignmentOperatorTokenizer instance2 = AssignmentOperatorTokenizer.INSTANCE;
//
//        // Then
//        assertNotNull(instance1, "Singleton instance should not be null");
//        assertSame(instance1, instance2, "Multiple references should point to the same singleton instance");
//        assertTrue(instance1 instanceof NonDelimitedTokenizer,
//                "AssignmentOperatorTokenizer should inherit from NonDelimitedTokenizer");
//    }
//
//    @DisplayName("Test tokenizing all assignment operators")
//    @ParameterizedTest(name = "should tokenize {0} correctly")
//    @ValueSource(strings = {
//            "=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>="
//    })
//    void tokenizeSingleOperators(String operator) {
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(operator);
//
//        // Then
//        assertEquals(1, tokens.size(), "Should produce exactly one token for " + operator);
//        Token token = tokens.get(0);
//        assertEquals(TokenType.ASSIGN, token.getType(), "Token should have ASSIGN type");
//        assertEquals(operator, token.getValue(), "Token value should match input");
//        assertEquals(0, token.getPosition(), "Token should start at position 0");
//        assertEquals(operator.length(), token.getLength(), "Token length should match operator length");
//    }
//
//    @DisplayName("Test tokenizing multiple operators in a string")
//    @Test
//    void tokenizeMultipleOperators() {
//        // Given
//        String input = "a = b += c *= d";
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(3, tokens.size(), "Should find three assignment operators");
//
//        assertEquals("=", tokens.get(0).getValue(), "First token should be =");
//        assertEquals(2, tokens.get(0).getPosition(), "First token should be at position 2");
//
//        assertEquals("+=", tokens.get(1).getValue(), "Second token should be +=");
//        assertEquals(6, tokens.get(1).getPosition(), "Second token should be at position 6");
//
//        assertEquals("*=", tokens.get(2).getValue(), "Third token should be *=");
//        assertEquals(11, tokens.get(2).getPosition(), "Third token should be at position 11");
//
//        tokens.forEach(token -> assertEquals(TokenType.ASSIGN, token.getType(),
//                "All tokens should have ASSIGN type"));
//    }
//
//    @DisplayName("Test tokenizing embedded operators")
//    @Test
//    void tokenizeEmbeddedOperators() {
//        // Given
//        String input = "result=value1+=value2*=factor";
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(3, tokens.size(), "Should find three assignment operators");
//
//        assertEquals("=", tokens.get(0).getValue(), "First token should be =");
//        assertEquals(6, tokens.get(0).getPosition(), "First token should be at position 6");
//
//        assertEquals("+=", tokens.get(1).getValue(), "Second token should be +=");
//        assertEquals(13, tokens.get(1).getPosition(), "Second token should be at position 13");
//
//        assertEquals("*=", tokens.get(2).getValue(), "Third token should be *=");
//        assertEquals(20, tokens.get(2).getPosition(), "Third token should be at position 20");
//    }
//
//    @DisplayName("Test that non-assignment operators are not tokenized")
//    @ParameterizedTest(name = "should not tokenize {0}")
//    @ValueSource(strings = {
//            "+", "-", "*", "/", "%", "&", "|", "^", "<<", ">>", ">>>",
//            "==", "!=", "<=", ">=", "<", ">", "&&", "||"
//    })
//    void shouldNotTokenizeNonAssignmentOperators(String operator) {
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(operator);
//
//        // Then
//        assertTrue(tokens.isEmpty(), "Should not tokenize non-assignment operator: " + operator);
//    }
//
//    @DisplayName("Test mixed code with assignment and non-assignment operators")
//    @Test
//    void tokenizeMixedCode() {
//        // Given
//        String input = "if (x == y) { z += 10; } else if (a != b) { c *= d; }";
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(2, tokens.size(), "Should only tokenize the assignment operators");
//
//        assertEquals("+=", tokens.get(0).getValue(), "First token should be +=");
//        assertEquals("*=", tokens.get(1).getValue(), "Second token should be *=");
//    }
//
//    @DisplayName("Test tokenizing code with all assignment operators")
//    @Test
//    void tokenizeAllOperatorsInCode() {
//        // Given
//        String input = "a = 1; b += 2; c -= 3; d *= 4; e /= 5; f %= 6; " +
//                "g &= 0xFF; h |= 0x0F; i ^= 0xF0; j <<= 2; k >>= 2; l >>>= 2;";
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(12, tokens.size(), "Should tokenize all 12 assignment operators");
//
//        String[] expectedOperators = {"=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>="};
//        for (int i = 0; i < expectedOperators.length; i++) {
//            assertEquals(expectedOperators[i], tokens.get(i).getValue(),
//                    "Token " + i + " should be " + expectedOperators[i]);
//            assertEquals(TokenType.ASSIGN, tokens.get(i).getType(),
//                    "Token " + i + " should have ASSIGN type");
//        }
//    }
//
//    @DisplayName("Test handling of null input")
//    @Test
//    void handleNullInput() {
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(null);
//
//        // Then
//        assertNotNull(tokens, "Result should not be null for null input");
//        assertTrue(tokens.isEmpty(), "Result should be empty for null input");
//    }
//
//    @DisplayName("Test handling of empty input")
//    @Test
//    void handleEmptyInput() {
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize("");
//
//        // Then
//        assertTrue(tokens.isEmpty(), "Result should be empty for empty input");
//    }
//
//    @DisplayName("Test that regex pattern works correctly")
//    @Test
//    void testRegexPattern() {
//        // Given - the pattern from AssignmentOperatorTokenizer
//        String regex = "=|\\+=|-=|\\*=|/=|%=|&=|\\|=|\\^=|<<=|>>=|>>>=";
//        Pattern pattern = Pattern.compile(regex);
//
//        // Test all valid operators
//        String[] validOperators = {"=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>="};
//        for (String op : validOperators) {
//            assertTrue(pattern.matcher(op).matches(), "Pattern should match " + op);
//        }
//
//        // Test some invalid operators
//        String[] invalidOperators = {"+", "-", "*", "/", "%", "&", "|", "^", "<<", ">>", ">>>", "==", "!="};
//        for (String op : invalidOperators) {
//            assertFalse(pattern.matcher(op).matches(), "Pattern should not match " + op);
//        }
//    }
//
//    @DisplayName("Test boundary cases with assignment operators in context")
//    @Test
//    void testBoundaryCases() {
//        // Given
//        String input = "==+=====";  // Contains ==, += and ===
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(2, tokens.size(), "Should find two assignment operators");
//        assertEquals("=", tokens.get(0).getValue(), "Should find = at start of ==");
//        assertEquals("+=", tokens.get(1).getValue(), "Should find += in the middle");
//    }
//
//    @DisplayName("Test edge case with operators at string boundaries")
//    @Test
//    void testOperatorsAtBoundaries() {
//        // Given
//        String input = "=code+=";
//
//        // When
//        List<Token> tokens = AssignmentOperatorTokenizer.INSTANCE.tokenize(input);
//
//        // Then
//        assertEquals(2, tokens.size(), "Should find two assignment operators");
//        assertEquals("=", tokens.get(0).getValue(), "Should find = at the beginning");
//        assertEquals("+=", tokens.get(1).getValue(), "Should find += at the end");
//    }
//}