package org.javaculator.antlr4.handlers.additive;

import org.antlr.v4.runtime.tree.ParseTree;
import org.javaculator.antlr4.Javaculator;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.utils.ParserCtxUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdditionTest {
    private final Javaculator calculator = new Javaculator();

    @Nested
    class IntegerLiterals {
        @BeforeEach
        void init() {
            calculator.clear();
        }

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable, expectedValue
                "x=1+2, x, 3",
                "x=+1+2, x, 3",
                "x=-1+2, x, 1",
                "x=0+0, x, 0",
                "x=1+2+3, x, 6",
                "x=1_2+3, x, 15",          // 12 + 3 = 15
                "x=0xA+1, x, 11",          // hexadecimal 0xA (10) + 1 = 11
                "x=01+1, x, 2",            // octal 01 (1) + 1 = 2
                "x=0b1010+0b1, x, 11",     // binary 0b1010 (10) + 0b1 (1) = 11
                "x=+ +2, x, 2",               // empty operand between '+' signs
        })
        public void testValidAdditionOperations(String input, String key, BigDecimal expected) {
            calculator.prepareAndInvokeCalculation(input);
            assertEquals(expected, calculator.getCache().get(key));
        }

        // ------------------ Invalid Addition Operations ------------------

        @ParameterizedTest
        @CsvSource({
                // Format: expression, variable
                "x=1+, x",       // missing right-hand operand
                "x=+1+, x",      // missing right-hand operand
                "x=1++2, x",     // ambiguous: the token '++' is not valid in this context
                "x=1+*2, x",     // invalid operator '*' after '+'
                "x=1+ , x"       // trailing '+' with missing operand
        })
        public void testInvalidAdditionOperations(String input, String key) {
            calculator.prepareAndInvokeCalculation(input);
            assertNull(calculator.getCache().get(key));
        }
    }
}
