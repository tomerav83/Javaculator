package org.javaculator.antlr4.handlers.additive;

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

public class AdditiveAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class ValidAdditiveAssignments {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x = 1 + 1, x:2",
                "x = 10 + 20, x:30",
                "x = 100 - 50, x:50",
                "x = 5 - 10, x:-5",
                "x = 1_000 + 2_000, x:3000",
                "x = 9_999 - 9_998, x:1",
                "x = 0 + 0, x:0",
                "x = 0 - 0, x:0",
        })
        void testValidIntegersAdditiveAssignment(String input,
                                               @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0x10 + 0x20, x:48",
                "x = 0XFF - 0X1, x:254",
                "x = 0xABC + 0xDEF, x:6315",
                "x = 0X1_000 - 0xFFF, x:1",
                "x = 0x0 + 0x0, x:0",
        })
        void testValidHexIntegersAdditiveAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 010 + 020, x:24",
                "x = 0100 - 077, x:1",
                "x = 01_234 + 05_670, x:3836",
                "x = 00 + 00, x:0",
        })
        void testValidOctalIntegersAdditiveAssignment(String input,
                                                    @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0b1010 + 0b0101, x:15",
                "x = 0B1111 - 0B1010, x:5",
                "x = 0b1_0000 - 0b1, x:15",
                "x = 0B0 + 0B0, x:0",
        })
        void testValidBinaryIntegersAdditiveAssignment(String input,
                                                     @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 10 + 0x10, x:26",
                "x = 0xFF - 100, x:155",
                "x = 0b1010 + 010, x:18",
                "x = 0777 - 0xFF, x:256",
        })
        void testValidMixedAdditiveAssignment(String input,
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
        void testValidLongSuffixAdditiveAssignment(String input,
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
        void testValidFPAdditiveAssignment(String input,
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
        void testValidHexFPSuffixAdditiveAssignment(String input,
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
        void testValidMixedFPAdditiveAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }
    }

    @Nested
    class InvalidAdditiveAssignments {
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
        void testInvalidLiteralsInAdditiveAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        // TODO FIX IT
        @ParameterizedTest
        @ValueSource(strings = {
                "x = 10 + .",
        })
        void testInvalidFPLiteralAdditiveAssignment(String input) {
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
        void testInvalidFPExponentsAdditiveAssignment(String input) {
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
        void testInvalidHexFPAdditiveAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);
            Assertions.assertEquals(0, calculator.getCache().size());
        }
    }
}
