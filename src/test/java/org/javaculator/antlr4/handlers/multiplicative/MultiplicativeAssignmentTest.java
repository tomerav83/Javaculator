package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.javaculator.antlr4.utils.CsvMapConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Map;

public class MultiplicativeAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class ValidMultiplicativeAssignments {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x = 1 * 1, x:1",
                "x = 1 / 1, x:1",
                "x = 1 ^ 1, x:1",
                "x = 1 % 1, x:0",
                "x = 10 * 20, x:200",
                "x = 10 / 20, x:0.5",
                "x = 10 ^ 20, x:100000000000000000000",
                "x = 10 % 20, x:10",
                "x = 1_000 * 2_000, x:2000000",
                "x = 1_000 / 2_000, x:0.5",
                "x = 1_000 ^ 2_000, x:1.000000000000000000000000000000000E+6000",
                "x = 1_000 % 2_000, x:1000",
        })
        void testValidIntegersMultiplicativeAssignment(String input,
                                                       @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.get("x"), calculator.getCache().get("x"));
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0x10 * 0x20, x:512",
                "x = 0x10 / 0x20, x:0.5",
                "x = 0x10 ^ 0x20, x:3.402823669209384634633746074317682E+38",
                "x = 0x10 % 0x20, x:16",
                "x = 0xABC * 0xDEF, x:9802116",
                "x = 0xABC / 0xDEF, x:0.7703952901597981497056349873843566",
                "x = 0xABC ^ 0xDEF, x:9.390098723085880099781882454952562E+12266",
                "x = 0xABC % 0xDEF, x:2748",
                "x = 0X1_000 * 0xFFF, x:16773120",
                "x = 0X1_000 / 0xFFF, x:1.000244200244200244200244200244200",
                "x = 0X1_000 ^ 0xFFF, x:4.111373459680183388321594774247083E+14792",
                "x = 0X1_000 % 0xFFF, x:1"
        })
        void testValidHexIntegersMultiplicativeAssignment(String input,
                                                          @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.get("x"), calculator.getCache().get("x"));
        }

        @ParameterizedTest
        @CsvSource({
                "x = 010 * 020, x:128",
                "x = 010 / 020, x:0.5",
                "x = 010 ^ 020, x:281474976710656",
                "x = 010 % 020, x:8",
                "x = 0100 * 077, x:4032",
                "x = 0100 / 077, x:1.015873015873015873015873015873016",
                "x = 0100 ^ 077, x:6.156563468186637376918600015647440E+113",
                "x = 0100 % 077, x:1",
                "x = 01_234 * 05_670, x:2004000",
                "x = 01_234 / 05_670, x:0.2226666666666666666666666666666667",
                "x = 01_234 ^ 05_670, x:2.134948614826624739456338323013959E+8474",
                "x = 01_234 % 05_670, x:668",
        })
        void testValidOctalIntegersMultiplicativeAssignment(String input,
                                                            @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.get("x"), calculator.getCache().get("x"));
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0b1010 * 0b0101, x:50",
                "x = 0b1010 / 0b0101, x:02",
                "x = 0b1010 ^ 0b0101, x:100000",
                "x = 0b1010 % 0b0101, x:0",
                "x = 0B1111 * 0B1010, x:150",
                "x = 0B1111 / 0B1010, x:1.5",
                "x = 0B1111 ^ 0B1010, x:576650390625",
                "x = 0B1111 % 0B1010, x:5",
                "x = 0b1_0000 * 0b1, x:16",
                "x = 0b1_0000 / 0b1, x:16",
                "x = 0b1_0000 ^ 0b1, x:16",
                "x = 0b1_0000 % 0b1, x:0",
        })
        void testValidBinaryIntegersMultiplicativeAssignment(String input,
                                                             @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.get("x"), calculator.getCache().get("x"));
        }

        @ParameterizedTest
        @CsvSource({
                "x = 10 + 0x10, x:26",
                "x = 0xFF - 100, x:155",
                "x = 0b1010 + 010, x:18",
                "x = 0777 - 0xFF, x:256",
        })
        void testValidMixedMultiplicativeAssignment(String input,
                                                    @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 100L + 200l, x:300",
                "x = 0xFFL - 100L, x:155",
                "x = 010L + 10L, x:18",
                "x = 0b101L - 2L, x:3",
        })
        void testValidLongSuffixMultiplicativeAssignment(String input,
                                                         @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 1.5 + 2.5, x:4.0",
                "x = 10.75 - 0.25, x:10.5",
                "x = .5 + .5, x:1.0",
                "x = 10. - 5., x:5.0",
                "x = 1e2 + 2e2, x:300.0",
                "x = 3.5e1 - 1.5e1, x:20.0",
                "x = 1.0f + 2.0F, x:3.0",
                "x = 10.5d - 0.5D, x:10.0",
                "x = 1.0e2f + 2.0e2F, x:300.0",
                "x = 1.0e2d - 9.9e1D, x:1.0",
        })
        void testValidFPMultiplicativeAssignment(String input,
                                                 @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0x1.0p0 + 0x2.0p0, x:3.0",
                "x = 0X3.0P0 - 0X1.0P0, x:2.0",
                "x = 0x1.0p2 + 0x1.0p1, x:6.0",
                "x = 0X1.0P10 - 0X1.0P9, x:512.0",
                "x = 0x1.0p0f + 0x1.0p1F, x:3.0",
                "x = 0x1.0p2d - 0x1.0p1D, x:2.0",
        })
        void testValidHexFPSuffixMultiplicativeAssignment(String input,
                                                          @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 1.5 + 0x1.0p0, x:2.5",
                "x = 1e1 - 0x1.0p2, x:6.0",
                "x = 0x1.0p3 + 2.5, x:10.5",
                "x = 10 + 1.5, x:11.5",
                "x = 0xFF - 10.5, x:244.5",
                "x = 100 + 0x1.0p2, x:104.0",
                "x = 0b1010 - 2.5, x:7.5"
        })
        void testValidMixedFPMultiplicativeAssignment(String input,
                                                      @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }
    }

    @Nested
    class InvalidMultiplicativeAssignments {
        @BeforeEach
        void init() {
            calculator.clear();
        }


        @ParameterizedTest
        @ValueSource(strings = {
                "x = 10 +",
                "x = 10 -",
                "x = 10 ++ 20",
                "x = 10 -- 20",
        })
        void testInvalidInvalidOperandsAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }


        @ParameterizedTest
        @ValueSource(strings = {
                "x = 10 + _20",
                "x = _10 - 20",
                "x = 10_ + 20",
                "x = 10 - 20_",
                "x = 0x + 10",
                "x = 10 + 0x",
                "x = 0b + 10",
                "x = 10 + 0b",
        })
        void testInvalidLiteralsInMultiplicativeAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        // TODO FIX IT
        @ParameterizedTest
        @ValueSource(strings = {
                "x = 10 + .",
        })
        void testInvalidFPLiteralMultiplicativeAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 1e + 2",
                "x = 1 + 2e",
                "x = 1e10e20 + 3",
                "x = 3 + 1e10e20",
        })
        void testInvalidFPExponentsMultiplicativeAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 0x1.0 + 2",
                "x = 1 + 0x1.0",
                "x = 0x1p + 2",
                "x = 1 + 0x1p",
        })
        void testInvalidHexFPMultiplicativeAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(0, calculator.getCache().size());
        }
    }
}
