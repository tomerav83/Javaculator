package org.javaculator.antlr42po.handlers.assignment;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.assignment.impl.AssignmentExprHandler;
import org.javaculator.antlr42po.handlers.assignment.impl.DFExprHandler;
import org.javaculator.antlr42po.handlers.multiplicative.IMultiplicativeExpr;
import org.javaculator.antlr42po.handlers.multiplicative.impl.MultiplicativeExprHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class AssignmentExprHandlers {
    private static final Set<IAssignExpr> HANDLERS = Set.of(AssignmentExprHandler.INSTANCE, DFExprHandler.INSTANCE);

    public static Integer handle(Calc2Parser.ExpressionContext ctx,
                                 Map<String, Integer> vars,
                                 Function<Calc2Parser.ExpressionContext, Integer> expressionVisitor,
                                 Function<Calc2Parser.AdditiveExprContext, Integer> additiveVisitor) {
        IAssignExpr.Implementation impl = getImplementation(ctx);

        return switch (impl) {
            case ASSIGNMENT -> AssignmentExprHandler.INSTANCE.calculate(ctx, vars, expressionVisitor);
            case DF -> DFExprHandler.INSTANCE.calculate(ctx, additiveVisitor);
        };
    }

    private static IAssignExpr.Implementation getImplementation(Calc2Parser.ExpressionContext ctx) {
        return HANDLERS.stream()
                .map((IAssignExpr handler) -> handler.getImpl(ctx))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
