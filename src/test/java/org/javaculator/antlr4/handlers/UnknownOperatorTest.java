package org.javaculator.antlr4.handlers;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.Javaculator;
import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.additive.AdditiveExprHandler;
import org.javaculator.antlr4.handlers.assignment.AssignExprHandler;
import org.javaculator.antlr4.handlers.assignment.AugmentedAssignExprHandler;
import org.javaculator.antlr4.handlers.multiplicative.MultiplicativeExprHandler;
import org.javaculator.antlr4.handlers.unary.PostIncDecHandler;
import org.javaculator.antlr4.handlers.unary.PreIncDecHandler;
import org.javaculator.antlr4.handlers.unary.SignedUnaryExprHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class UnknownOperatorTest {
    private final Javaculator calculator = new Javaculator();

    @BeforeEach
    void init() {
        calculator.clear();
    }

    @Test
    @DisplayName("test unknown operator for additive handler")
    void testUnknownOperatorAdditiveHandler() {
        Function<CalcParser.MultiplicativeContext, BigDecimal> func =
                (CalcParser.MultiplicativeContext ctx) -> BigDecimal.ZERO;
        CalcParser.AddSubExprContext add = Mockito.mock(CalcParser.AddSubExprContext.class);
        CalcParser.MultiplicativeContext mult = Mockito.mock(CalcParser.MultiplicativeContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        Mockito.when(tree.getText()).thenReturn("not additive");
        Mockito.when(add.getChild(Mockito.anyInt())).thenReturn(tree);
        Mockito.when(add.multiplicative(Mockito.anyInt())).thenReturn(mult);
        Mockito.when(add.multiplicative()).thenReturn(List.of(mult, mult));

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> AdditiveExprHandler.INSTANCE.handle(add, func));
    }

    @Test
    @DisplayName("test unknown operator for assignment handler")
    void testUnknownOperatorAssignmentHandler() {
        Function<CalcParser.ExpressionContext, BigDecimal> func =
                (CalcParser.ExpressionContext ctx) -> BigDecimal.ZERO;
        CalcParser.AssignExprContext assign = Mockito.mock(CalcParser.AssignExprContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        Mockito.when(tree.getText()).thenReturn("not assignment");
        Mockito.when(assign.getChild(Mockito.anyInt())).thenReturn(tree);

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> AssignExprHandler.INSTANCE.handle(assign, null, func));
    }

    @Test
    @DisplayName("test unknown operator for augmented assignment handler")
    void testUnknownOperatorAugmentedAssignmentHandler() {
        RollbackCache cache = new RollbackCache();
        cache.putAndGetCurrent("some identifier", BigDecimal.ONE);
        Function<CalcParser.ExpressionContext, BigDecimal> func =
                (CalcParser.ExpressionContext ctx) -> BigDecimal.ZERO;
        CalcParser.AugmentedAssignExprContext assign = Mockito.mock(CalcParser.AugmentedAssignExprContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        TerminalNode node = Mockito.mock(TerminalNode.class);
        Mockito.when(tree.getText()).thenReturn("not augmented assignment");
        Mockito.when(assign.getChild(Mockito.anyInt())).thenReturn(tree);
        Mockito.when(node.getText()).thenReturn("some identifier");
        Mockito.when(assign.getToken(Mockito.anyInt() , Mockito.anyInt())).thenReturn(node);

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> AugmentedAssignExprHandler.INSTANCE.handle(assign, cache, func));
    }

    @Test
    @DisplayName("test unknown operator for multiplicative handler")
    void testUnknownOperatorMultiplicativeHandler() {
        Function<CalcParser.UnaryContext, BigDecimal> func =
                (CalcParser.UnaryContext ctx) -> BigDecimal.ZERO;
        CalcParser.MulDivModExprContext mult = Mockito.mock(CalcParser.MulDivModExprContext.class);
        CalcParser.UnaryContext unary = Mockito.mock(CalcParser.UnaryContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        Mockito.when(tree.getText()).thenReturn("not multiplicative");
        Mockito.when(mult.getChild(Mockito.anyInt())).thenReturn(tree);
        Mockito.when(mult.unary(Mockito.anyInt())).thenReturn(unary);
        Mockito.when(mult.unary()).thenReturn(List.of(unary, unary));

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> MultiplicativeExprHandler.INSTANCE.handle(mult, func));
    }

    @Test
    @DisplayName("test unknown operator for post inc/dec handler")
    void testUnknownOperatorPostIncDecHandler() {
        RollbackCache cache = new RollbackCache();
        cache.putAndGetCurrent("some identifier", BigDecimal.ONE);
        CalcParser.PostIncDecExprContext postIncDec = Mockito.mock(CalcParser.PostIncDecExprContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        TerminalNode node = Mockito.mock(TerminalNode.class);
        Mockito.when(tree.getText()).thenReturn("not post inc/dec");
        Mockito.when(postIncDec.getChild(Mockito.anyInt())).thenReturn(tree);
        Mockito.when(node.getText()).thenReturn("some identifier");
        Mockito.when(postIncDec.getToken(Mockito.anyInt() , Mockito.anyInt())).thenReturn(node);

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> PostIncDecHandler.INSTANCE.handle(postIncDec, cache));
    }

    @Test
    @DisplayName("test unknown operator for pre inc/dec handler")
    void testUnknownOperatorPreIncDecHandler() {
        RollbackCache cache = new RollbackCache();
        cache.putAndGetCurrent("some identifier", BigDecimal.ONE);
        CalcParser.PreIncDecExprContext preIncDec = Mockito.mock(CalcParser.PreIncDecExprContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        TerminalNode node = Mockito.mock(TerminalNode.class);
        Mockito.when(tree.getText()).thenReturn("not post inc/dec");
        Mockito.when(preIncDec.getChild(Mockito.anyInt())).thenReturn(tree);
        Mockito.when(node.getText()).thenReturn("some identifier");
        Mockito.when(preIncDec.getToken(Mockito.anyInt() , Mockito.anyInt())).thenReturn(node);

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> PreIncDecHandler.INSTANCE.handle(preIncDec, cache));
    }

    @Test
    @DisplayName("test unknown operator for signed handler")
    void testUnknownOperatorSignedHandler() {
        Function<CalcParser.UnaryContext, BigDecimal> func =
                (CalcParser.UnaryContext ctx) -> BigDecimal.ZERO;
        CalcParser.SignedExprContext signed = Mockito.mock(CalcParser.SignedExprContext.class);
        ParseTree tree = Mockito.mock(ParseTree.class);
        TerminalNode node = Mockito.mock(TerminalNode.class);
        Mockito.when(tree.getText()).thenReturn("not post inc/dec");
        Mockito.when(signed.getChild(Mockito.anyInt())).thenReturn(tree);

        Assertions.assertThrows(UnknownOperatorException.class,
                () -> SignedUnaryExprHandler.INSTANCE.handle(signed, func));
    }
}
