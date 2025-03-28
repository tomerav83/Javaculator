//package org.javaculator.antlr4.handlers.primary;
//
//import org.javaculator.antlr4.Calc2Parser;
//import org.javaculator.antlr4.CalculationProcessor;
//import org.javaculator.antlr4.exceptions.InvalidCalculationImplException;
//import org.javaculator.antlr4.handlers.primary.impl.IdentifierExprHandler;
//import org.javaculator.antlr4.handlers.primary.impl.LiteralExprHandler;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.converter.ConvertWith;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import java.util.Map;
//
//public class RootPrimaryExprHandlersTest {
//    @Nested
//    class LiteralExprHandlerTests {
//        @BeforeEach
//        void init() {
//            CalculationProcessor.clearState();
//        }
//
//        @Test
//        @DisplayName("test invalid calculate impl call - case1")
//        void testInvalidCalculateImplCallCase1() {
//            Assertions.assertThrows(InvalidCalculationImplException.class,
//                    () -> LiteralExprHandler.INSTANCE.calculate(null, Map.of()));
//        }
//
//        @Test
//        @DisplayName("test invalid calculate impl call - case2")
//        void testInvalidCalculateImplCallCase2() {
//            Assertions.assertThrows(InvalidCalculationImplException.class,
//                    () -> LiteralExprHandler.INSTANCE.calculate(null, (Calc2Parser.ExpressionContext ctx) -> 0));
//        }
//
//        @ParameterizedTest(name = "Primary expression: Type=Literal, value={0}")
//        @CsvSource({
//                "l = 42, l:42", "l = -1, l:-1", "l = +1, l:1",
//                "l = (42), l:42", "l = (-1), l:-1", "l = (+1), l:1",
//                "l = (((42))), l:42", "l = (((-1))), l:-1", "l = (((+1))), l:1"
//        })
//        void testValidPrimaryExprLiterals(String input,
//                                          @ConvertWith(CsvMapConverter.class) Map<String, Integer> expected) {
//            Assertions.assertEquals(expected, CalculationProcessor.calculate(input));
//        }
//
//
//        @ParameterizedTest(name = "Primary expression: Type=Literal, value={0}")
//        @CsvSource({
//                "l = a42", "l = -a1", "l = +a1",
//                "l = ++1", "l = --1",
//                "l = 1++", "l = 1--",
//                "l = ()",  "l = ((()))"
//        })
//        void testInvalidPrimaryExprLiterals(String input) {
//            Assertions.assertEquals(0, CalculationProcessor.calculate(input).size());
//        }
//    }
//
//    @Nested
//    class IdentifierExprHandlerTests {
//        @BeforeEach
//        void init() {
//            CalculationProcessor.clearState();
//        }
//
//        @Test
//        @DisplayName("test invalid calculate impl call - case1")
//        void testInvalidCalculateImplCallCase1() {
//            Assertions.assertThrows(InvalidCalculationImplException.class,
//                    () -> IdentifierExprHandler.INSTANCE.calculate(null));
//        }
//
//        @Test
//        @DisplayName("test invalid calculate impl call - case2")
//        void testInvalidCalculateImplCallCase2() {
//            Assertions.assertThrows(InvalidCalculationImplException.class,
//                    () -> IdentifierExprHandler.INSTANCE.calculate(null, (Calc2Parser.ExpressionContext ctx) -> 0));
//        }
//
//        @ParameterizedTest(name = "Primary expression: Type=Identifier, value={0}")
//        @CsvSource({
//                "l = 42, l:42", "l = -1, l:-1", "l = +1, l:1",
//                "l = (42), l:42", "l = (-1), l:-1", "l = (+1), l:1",
//                "l = (((42))), l:42", "l = (((-1))), l:-1", "l = (((+1))), l:1"
//        })
//        void testInvalidPrimaryExprIdentifiers(String input,
//                                               @ConvertWith(CsvMapConverter.class) Map<String, Integer> expected) {
//            Assertions.assertEquals(0, CalculationProcessor.calculate(input).size());
//
//        }
//
//
//        @ParameterizedTest(name = "Primary expression: Type=Literal, value={0}")
//        @CsvSource({
//                "x "
//        })
//        void testValidPrimaryExprIdentifiers(String input,  @ConvertWith(CsvMapConverter.class) Map<String, Integer> expected) {
//            Assertions.assertEquals(expected, CalculationProcessor.calculate(input));
//        }
//    }
//}
