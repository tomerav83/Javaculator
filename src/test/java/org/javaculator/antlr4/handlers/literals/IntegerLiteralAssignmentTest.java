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

public class IntegerLiteralAssignmentTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class ValidIntegerLiteralAssignment {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0, x:0",
                "x = 42, x:42",
                "x = 1234567890, x:1234567890",
                "x = 9_8_7_6_5, x:98765",
                "x = 123L, x:123",
                "x = 456l, x:456",
                "x = -456, x:-456",
                "x = +456, x:456",
        })
        void testValidIntegerLiteralAssignment(String input,
                                               @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0x0, x:0",
                "x = 0X0, x:0",
                "x = 0xabc, x:2748",
                "x = 0XDEF, x:3567",
                "x = 0x1_2_3, x:291",
                "x = 0xFFFl, x:4095",
                "x = 0XABCL, x:2748",
                "x = -0XABCL, x:-2748",
                "x = +0XABCL, x:2748",
        })
        void testValidHexIntegerLiteralAssignment(String input,
                                                  @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 00, x:0",
                "x = 01234567, x:342391",
                "x = 01_2_3, x:83",
                "x = 0777l, x:511",
                "x = 0777L, x:511",
                "x = -0777L, x:-511",
                "x = +0777L, x:511",
        })
        void testValidOctalIntegerLiteralAssignment(String input,
                                                    @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }

        @ParameterizedTest
        @CsvSource({
                "x = 0b0, x:0",
                "x = 0B1, x:1",
                "x = 0b1010, x:10",
                "x = 0B1_0_1_0, x:10",
                "x = 0b1010l, x:10",
                "x = 0B1111L, x:15",
                "x = -0B1111L, x:-15",
                "x = +0B1111L, x:15",
        })
        void testValidBinaryIntegerLiteralAssignment(String input,
                                                     @ConvertWith(CsvMapConverter.class) Map<String, BigDecimal> expected) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(expected.size(), calculator.getCache().size());
        }
    }
    @Nested
    class InvalidIntegerLiteralAssignment {
        @BeforeEach
        void init() {
            calculator.clear();
        }


        @ParameterizedTest
        @ValueSource(strings = {
                "x = _",
                "x = 123_L",
                "x = 123_l",
                "x = _123",
                "x = 123_",
                "x = 123LL",
                "x = 123lL",
                "x = 123Ll"
        })
        void testInvalidIntegerLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 0x",
                "x = 0X",
                "x = 0xG",
                "x = 0XZ",
                "x = 0x_",
                "x = 0X_123",
                "x = 0x123_",
                "x = 0XFFL_",
                "x = 0x123LL",
        })
        void testInvalidHexIntegerLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 0_",
                "x = 08",
                "x = 09",
                "x = 0778",
                "x = 01234_L",
                "x = 0123_",
                "x = 0123LL",
        })
        void testInvalidOctalIntegerLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "x = 0b",
                "x = 0B",
                "x = 0b2",
                "x = 0B3",
                "x = 0b_",
                "x = 0B_101",
                "x = 0b101_",
                "x = 0b101_L",
                "x = 0b101LL",
        })
        void testInvalidBinaryIntegerLiteralAssignment(String input) {
            calculator.prepareAndInvokeCalculation(input);

            Assertions.assertEquals(0, calculator.getCache().size());
        }
    }
}
