package org.javaculator.tokenization.impl.identifiers;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PrimitiveTokenizerTest {
    @ParameterizedTest(name = "Should match standalone primitive types: {0}")
    @ValueSource(strings = {
            "byte", "short", "int", "long", "float", "double", "boolean", "char"
    })
    void shouldMatchStandalonePrimitiveTypes(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match primitive types in variable declarations: {0}")
    @ValueSource(strings = {
            "int count = 0;",
            "boolean isActive = true;",
            "char letter = 'a';",
            "float price = 10.99f;",
            "double amount = 123.456;",
            "byte flags = 0x1F;",
            "short value = 100;",
            "long bigNumber = 1000000L;"
    })
    void shouldMatchPrimitiveTypesInVariableDeclarations(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match primitive types with spaces and comments: {0}")
    @ValueSource(strings = {
            "public static int getCount() { return 0; }",
            "private boolean /* hidden */ flag;",
            "protected double  distance; // with extra spaces",
            "  float   spaced  ;",
            "/*comment*/ char c;"
    })
    void shouldMatchPrimitiveTypesWithSpacesAndComments(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match multiple primitive types in one string: {0}")
    @ValueSource(strings = {
            "int x; float y; double z;",
            "boolean flag1, flag2; char letter;",
            "I need byte, short, and long variables",
            "The primitive types are: int, char, boolean, float, double, byte, short, and long."
    })
    void shouldMatchMultiplePrimitiveTypes(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match primitive types at word boundaries: {0}")
    @ValueSource(strings = {
            "return int;",
            "(boolean)",
            "x instanceof char",
            "Type: double",
            "<long>",
            "I love byte!"
    })
    void shouldMatchPrimitiveTypesAtWordBoundaries(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should not match dollar-prefixed primitive types: {0}")
    @ValueSource(strings = {
            "$int",
            "$byte count = 0;",
            "$boolean isValid;",
            "$char letter;",
            "$float price;",
            "$double amount;",
            "$short value;",
            "$long number;"
    })
    void shouldNotMatchDollarPrefixedPrimitiveTypes(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match primitive type substrings within other words: {0}")
    @ValueSource(strings = {
            "byteCode",
            "shorthand",
            "integer",
            "longhorn",
            "floater",
            "doubleclick",
            "booleanValue",
            "charset",
            "intrepid",
            "alongshore",
            "bytewise",
            "shortening",
            "Pinteger",
            "chromosome"
    })
    void shouldNotMatchPrimitiveTypeSubstrings(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match case variations of primitive types: {0}")
    @ValueSource(strings = {
            "Byte",
            "Short",
            "Int",
            "Long",
            "Float",
            "Double",
            "Boolean",
            "Char",
            "BYTE",
            "SHORT",
            "INT",
            "LONG",
            "FLOAT",
            "DOUBLE",
            "BOOLEAN",
            "CHAR"
    })
    void shouldNotMatchCaseVariationsOfPrimitiveTypes(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match similar but incorrect types: {0}")
    @ValueSource(strings = {
            "bytes",
            "shorts",
            "integer",
            "longer",
            "floating",
            "doubles",
            "bool",
            "character",
            "bytee",
            "shortt",
            "intx",
            "longg",
            "flooat",
            "doublle",
            "booleann",
            "characcter"
    })
    void shouldNotMatchSimilarButIncorrectTypes(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Should not match empty or whitespace input: {0}")
    @ValueSource(strings = {
            "",
            " ",
            "  ",
            "\t",
            "\n",
            "\r\n"
    })
    void shouldNotMatchEmptyOrWhitespaceInput(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @Test
    void shouldNotMatchNullInput() {
        Assertions.assertEquals(0, PrimitiveTypeTokenizer.INSTANCE.tokenize(null).size());
    }

    @ParameterizedTest(name = "Should not match dollar-prefixed types with surrounding text: {0}")
    @ValueSource(strings = {
            "The variable $int contains a value",
            "Use $boolean for flags",
            "This $double is important",
            "My $char variable",
            "A $float precision number",
            "That $long integer",
            "Convert to $byte array",
            "This is a $short value"
    })
    void shouldNotMatchDollarPrefixedTypesWithSurroundingText(String input) {
        Truth.assertThat(PrimitiveTypeTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }
}
