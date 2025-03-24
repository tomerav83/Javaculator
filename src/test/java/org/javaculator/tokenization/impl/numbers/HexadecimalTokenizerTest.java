package org.javaculator.tokenization.impl.numbers;

import com.google.common.truth.Truth;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HexadecimalTokenizerTest {
    @ParameterizedTest(name = "Should match single digit hex value: {0}")
    @ValueSource(strings = {
            "0x1",
            "0X1",
            "0xa",
            "0XA",
            "0x0",
            "0X0",
            "0xf",
            "0XF"
    })
    void shouldMatchBasicSingleDigitHex(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match multi-digit hex value: {0}")
    @ValueSource(strings = {
            "0x123",
            "0X123",
            "0xabc",
            "0XABC",
            "0xABC",
            "0Xabc",
            "0x123ABC",
            "0X123abc"
    })
    void shouldMatchMultiDigitHex(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match hex with long suffix value: {0}")
    @ValueSource(strings = {
            "0x1L",
            "0X1l",
            "0xaL",
            "0XAl",
            "0x123L",
            "0X123l",
            "0xabcL",
            "0XABCl"
    })
    void shouldMatchHexWithLongSuffix(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match hex with underscore seperator value: {0}")
    @ValueSource(strings = {
            "0x1_2",
            "0X1_2",
            "0xa_b",
            "0XA_B",
            "0x1_2_3",
            "0X1_2_3",
            "0xa_b_c",
            "0XA_B_C",
            "0x1_23_4_56",
            "0xa_BC_d_EF"
    })
    void shouldMatchHexWithUnderscores(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Should match hex with underscore seperator and long suffix value: {0}")
    @ValueSource(strings = {
            "0x1_2L",
            "0X1_2l",
            "0xa_b_cL",
            "0XA_B_Cl",
            "0x1_2_3_4L",
            "0X1_2_3_4l"
    })
    void shouldMatchHexWithUnderscoresAndLongSuffix(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0x0",
            "0X0",
            "0xFFFFFFFF",
            "0XFFFFFFFF",
            "0x7FFFFFFF",
            "0X7FFFFFFF",
            "0x80000000",
            "0X80000000",
            "0xFFFFFFFFFFFFFFFFL",
            "0XFFFFFFFFFFFFFFFFL",
            "0x7FFFFFFFFFFFFFFFL",
            "0X7FFFFFFFFFFFFFFFL",
            "0x8000000000000000L",
            "0X8000000000000000L"
    })
    void shouldMatchHexEdgeCases(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isNotEmpty();
    }

    @ParameterizedTest(name = "Shouldn't match invalid hex value: {0}")
    @ValueSource(strings = {
            "0x",
            "0X",
            "0xG",
            "0XZ",
            "0x_1",
            "0X_A",
            "0x1_",
            "0XA_",
            "0xLL",
            "0XlL",
            "0x123LL",
            "0XABClL"
    })
    void shouldNotMatchInvalidHexFormat(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Shouldn't match invalid context hex value: {0}")
    @ValueSource(strings = {
            "a0x1",
            "A0X1",
            "_0x1",
            "_0X1",
            "10x1",
            "20X1",
            "0x1_",
            "0X1_",
            "a0x1a",
            "A0X1A",
            "word0x1word",
            "WORD0XAWORD",
            "var_0x1_val",
            "VAR_0XA_VAL",
            "hex0x123",
            "HEX0X123"
    })
    void shouldNotMatchWithInvalidContext(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }


    @ParameterizedTest(name = "Shouldn't match invalid hex value: {0}")
    @ValueSource(strings = {
            "0xL",
            "0Xl",
            "x123",
            "X123",
            "0#123",
            "0$ABC",
            "0x_",
            "0X_"
    })
    void shouldNotMatchInvalidHexEdgeCases(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }

    @ParameterizedTest(name = "Shouldn't match invalid other type literals hex value: {0}")
    @ValueSource(strings = {
            "0b101",
            "0B101",
            "0123",
            "0567",
            "123",
            "456",
            "123.456",
            "0.123",
            "123e45",
            "123E+45",
            "123f",
            "123F",
            "123d",
            "123D"
    })
    void shouldNotMatchOtherNumberTypes(String input) {
        Truth.assertThat(HexadecimalTokenizer.INSTANCE.tokenize(input)).isEmpty();
    }
}
