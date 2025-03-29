package org.javaculator.antlr4.handlers.literals;

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

public class FloatingPointLiteralAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class ValidFPLiteralAssignment {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0.0, x:0.0",
                "x = 1.0, x:1.0",
                "x = 123.456, x:123.456",
                "x = 0.123, x:0.123",
                "x = .123, x:0.123",
                "x = 123., x:123.0",
                "x = 1_234.5_67, x:1234.567",
        })
        void testValidFPLiteralAssignment(String input,
                                          @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0.0f, x:0.0",
                "x = 1.0F, x:1.0",
                "x = 123.456d, x:123.456",
                "x = 0.123D, x:0.123",
        })
        void testValidSuffixedFPLiteralAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 1e10, x:1.0E10",
                "x = 1E-10, x:1.0E-10",
                "x = 1.5e+20, x:1.5E20",
                "x = 1.5E+20, x:1.5E20",
                "x = 1.5e+20d, x:1.5E20",
                "x = 1.5e+20f, x:1.5E20",
                "x = 1e10F, x:1.0E10",
                "x = 1E-10D, x:1.0E-10",
                "x = 1_2e1_0, x:12.0E10",
                "x = 0e0, x:0.0",
        })
        void testValidExponentFPLiteralAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0x1.0p0, x:1.0",
                "x = 0X1.0P0, x:1.0",
                "x = 0x1.0p0f, x:1.0",
                "x = 0X1.0P0F, x:1.0",
                "x = 0x1.0p0d, x:1.0",
                "x = 0X1.0P0D, x:1.0",
                "x = 0x1p0, x:1.0",
                "x = 0x1.p0, x:1.0",
                "x = 0x.1p0, x:0.0625",
                "x = 0x1.0p-1, x:0.5",
                "x = 0x1.0p+1, x:2.0",
                "x = 0x1_0.1p1_0, x:16.0625E10"
        })
        void testValidHexadecimalExponentFPLiteralAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }
    }

    @Nested
    class InvalidFPLiteralAssignment {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = .",
                "x = .f",
                "x = .d",
                "x = 1.0fd",
                "x = 1.0Df",
                "x = 1.0ff",
                "x = 1.0FF",
                "x = 1.0dd",
                "x = 1.0DD",
                "x = _1.0",
                "x = 1_.0",
                "x = 1._0",
                "x = 1.0_",
                "x = 1.0_f",
                "x = 1.0_F",
                "x = 1.0_d",
                "x = 1.0_D",
        })
        void testInvalidFPLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = e10",
                "x = 1e",
                "x = 1E",
                "x = 1e+",
                "x = 1E-",
                "x = 1e10_",
                "x = 1e_10",
                "x = 1e10_f",
                "x = 1E10_F",
                "x = 1e10e20",
                "x = 1E10E20",
        })
        void testInvalidExponentsFPLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 0x",
                "x = 0X",
                "x = 0x.",
                "x = 0X.p0",
                "x = 0x1.0",
                "x = 0X1.0",
                //"x = 0x1.p",
                //"x = 0X1.P",
                "x = 0x1.0p",
                "x = 0X1.0P",
                "x = 0x1.0p+",
                "x = 0X1.0P-",
                "x = 0x1.0e10",
                "x = 0X1.0E10",
                "x = 0x1.0pG",
                "x = 0X1.0PA",
                "x = 0x1.0p0ff",
                "x = 0X1.0P0FF",
                "x = 0x1.0p0dd",
                "x = 0X1.0P0DD",
                "x = 0x_1.0p0",
                "x = 0x1_.0p0",
                "x = 0x1._0p0",
                "x = 0x1.0_p0",
                "x = 0x1.0p_0",
                "x = 0x1.0p0_",
                "x = 0x1.0p0_f",
                "x = 0X1.0P0_F",
                "x = 0x1.0p0_d",
                "x = 0X1.0P0_D"
        })
        void testInvalidHexadecimalExponentsFPLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }
    }
}
