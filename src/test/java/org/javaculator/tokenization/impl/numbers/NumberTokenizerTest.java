//package org.javaculator.tokenization.impl.numbers;
//
//import com.google.common.truth.Truth;
//import org.javaculator.tokenization.models.Token;
//import org.javaculator.tokenization.models.TokenType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test class for {@link NumberTokenizer}
// * Tests edge cases and regular functionality of the number tokenizer.
// * The tokenizer matches patterns: \d+\.?\d*|\.\\d+
// * Which covers:
// * - Integer numbers (\d+)
// * - Decimal numbers with optional fractional part (\d+\.\d*)
// * - Decimal numbers starting with decimal point (.\d+)
// */
//class NumberTokenizerTest {
//    @ParameterizedTest(name = "Should match integer values: {0}")
//    @ValueSource(strings = {
//            "0",
//            "1",
//            "42",
//            "123",
//            "9999",
//            "01234",
//            "000",
//            "987654321"
//    })
//    void shouldMatchIntegerValues(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match decimal values with leading digits: {0}")
//    @ValueSource(strings = {
//            "0.0",
//            "1.0",
//            "3.14",
//            "123.456",
//            "0.123",
//            "99.99",
//            "0.0000001",
//            "123456.789"
//    })
//    void shouldMatchDecimalValuesWithLeadingDigits(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match decimal values with trailing zeros: {0}")
//    @ValueSource(strings = {
//            "1.0",
//            "10.0",
//            "100.00",
//            "0.10",
//            "0.10000",
//            "123.4500000"
//    })
//    void shouldMatchDecimalValuesWithTrailingZeros(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match decimal values starting with decimal point: {0}")
//    @ValueSource(strings = {
//            ".0",
//            ".1",
//            ".123",
//            ".999",
//            ".0001",
//            ".987654321"
//    })
//    void shouldMatchDecimalValuesStartingWithDecimalPoint(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match numbers with special characters around them: {0}")
//    @ValueSource(strings = {
//            "$100",
//            "100$",
//            "(123)",
//            "[456]",
//            "{789}",
//            "100%",
//            "#1",
//            "!2!",
//            "@3@",
//            "^42^",
//            "&123&",
//            "-456-",
//            "+789+",
//            "/100/",
//            "\\200\\",
//            "\"300\"",
//            "'400'",
//            ";500;",
//            ":600:",
//            ",700,",
//            "_800_",
//            "|900|",
//            "<12.34>",
//            "~45.67~"
//    })
//    void shouldMatchNumbersWithSpecialCharactersAroundThem(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match numbers in sentences: {0}")
//    @ValueSource(strings = {
//            "The answer is 42",
//            "I need $15.99 for this",
//            "Chapter 7 of the book",
//            "It's currently 3:15 PM",
//            "In 2023, we saw growth of 25.5%",
//            "Temperature: 72.5 degrees",
//            "The room is 10x15 meters",
//            "Score: 100 points",
//            "We need (24) items",
//            "The distance is 42.195 kilometers"
//    })
//    void shouldMatchNumbersInSentences(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should match multiple numbers in a string: {0}")
//    @ValueSource(strings = {
//            "1 2 3",
//            "1.2 3.4 5.6",
//            "10,20,30",
//            "The cost is $10.99 but on sale for $8.50",
//            "Coordinates: 123.45, 678.90",
//            "Temperatures: 98.6 F, 37 C, 310.15 K",
//            "Pages 5-10 and 15-20",
//            "[1] [2] [3]"
//    })
//    void shouldMatchMultipleNumbersInString(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match numbers preceded by letters: {0}")
//    @ValueSource(strings = {
//            "a1",
//            "x42",
//            "abc123",
//            "temperature35",
//            "iPhone11",
//            "COVID19",
//            "B2B",
//            "A1234",
//            "z0.123"
//    })
//    void shouldNotMatchNumbersPrecededByLetters(String input) {
//        //TODO fix
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match numbers followed by letters: {0}")
//    @ValueSource(strings = {
//            "1a",
//            "42x",
//            "123abc",
//            "35celsius",
//            "11Pro",
//            "19pandemic",
//            "2B",
//            "1234A",
//            "0.123z"
//    })
//    void shouldNotMatchNumbersFollowedByLetters(String input) {
//        //TODO fix
//        //Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match numbers with letters on both sides: {0}")
//    @ValueSource(strings = {
//            "a1b",
//            "x42y",
//            "abc123def",
//            "temp35c",
//            "i11Pro",
//            "COVID19pandemic",
//            "B2C",
//            "A1234Z",
//            "z0.123x"
//    })
//    void shouldNotMatchNumbersWithLettersOnBothSides(String input) {
//        //TODO fix
//        //Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match malformed numeric patterns: {0}")
//    @ValueSource(strings = {
//            ".",
//            "..",
//            "1..",
//            "1.2.3",
//            "123.",
//            "--123",
//            "++456",
//            "..789",
//            "1,234",
//            "5,000.00"
//    })
//    void shouldNotMatchMalformedNumericPatterns(String input) {
//        //TODO fix
//        //Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match words without numbers: {0}")
//    @ValueSource(strings = {
//            "Hello",
//            "World",
//            "abc",
//            "xyz",
//            "Testing",
//            "NoNumbersHere",
//            "JustText",
//            "UPPERCASE",
//            "lowercase",
//            "PascalCase",
//            "camelCase",
//            "snake_case",
//            "kebab-case"
//    })
//    void shouldNotMatchWordsWithoutNumbers(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match empty or whitespace input: {0}")
//    @ValueSource(strings = {
//            "",
//            " ",
//            "  ",
//            "\t",
//            "\n",
//            "\r\n"
//    })
//    void shouldNotMatchEmptyOrWhitespaceInput(String input) {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//
//    @Test
//    void shouldNotMatchNullInput() {
//        Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(null)).isEmpty();
//    }
//
//    @ParameterizedTest(name = "Should not match valid units with letters attached: {0}")
//    @ValueSource(strings = {
//            "10kg",
//            "15cm",
//            "42mph",
//            "3hr",
//            "5min",
//            "20sec",
//            "100ft",
//            "30m",
//            "25GB",
//            "8TB",
//            "1.5MB",
//            "2.4GHz"
//    })
//    void shouldNotMatchValidUnitsWithLettersAttached(String input) {
//        //TODO fix
//        //Truth.assertThat(NumberTokenizer.INSTANCE.tokenize(input)).isEmpty();
//    }
//}