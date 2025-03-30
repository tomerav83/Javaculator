package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.Javaculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MultiplicationTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=2*3, x, 6",
                "x=+2*+3, x, 6",
                "x=-2*+3, x, -6",
                "x=2*3*4, x, 24",
                "x=1_0*2, x, 20",
                "x=0xA*2, x, 20",
                "x=01*2, x, 2",
                "x=0b1010*0b10, x, 20"
        })
        public void testValidMultiplicationOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        @ParameterizedTest
        @CsvSource({
                "x=2*, x",
                "x=*3, x",
                "x=2**3, x",
                "x=2* , x",
                "x=+*3, x"
        })
        public void testInvalidMultiplicationOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }

    @Nested
    class FloatLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                "x=2.0*3.0, x, 6.00",
                "x=+2.0*+3.0, x, 6.00",
                "x=-2.0*+3.0, x, -6.00",
                "x=0.0*5.0, x, 0.00",
                "x=2.0*3.0*4.0, x, 24.000",
                "x=1_0.0*2.0, x, 20.00",
                "x=0x1.8p10*2.0, x, 3072.00",
                "x=.5*2.0, x, 1.00",
                "x=1.*2.0, x, 2.0"
        })
        public void testValidFloatMultiplicationOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key), "Failed for input: " + input);
        }

        @ParameterizedTest
        @CsvSource({
                "x=2.0*, x",
                "x=*3.0, x",
                "x=2.0**3.0, x",
                "x=2.0* , x",
                "x=2_.0*3.0, x",
                "x=2._0*3.0, x",
                "x=2.0e*3.0, x",
                "x=0x1.8*3.0, x"
        })
        public void testInvalidFloatMultiplicationOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key), "Input should be invalid: " + input);
        }
    }
}
