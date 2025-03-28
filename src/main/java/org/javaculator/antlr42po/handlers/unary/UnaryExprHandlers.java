package org.javaculator.antlr42po.handlers.unary;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.handlers.primary.impl.IdentifierExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.LiteralExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.ParenthesisExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.PreOpExprHandler;
import org.javaculator.antlr42po.handlers.unary.impl.PrimaryExprHandler;
import org.javaculator.antlr42po.handlers.unary.impl.UnaryExprHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class UnaryExprHandlers {
    private static final Set<IUnaryExpr> HANDLERS = Set.of(UnaryExprHandler.INSTANCE, PrimaryExprHandler.INSTANCE);

    public static Integer handle(Calc2Parser.UnaryExprContext ctx,
                                 Map<String, Integer> vars,
                                 Function<Calc2Parser.UnaryExprContext, Integer> unaryVisitor,
                                 Function<Calc2Parser.PrimaryExprContext, Integer> primaryVisitor) {
        IUnaryExpr.Implementation impl = getImplementation(ctx);

        return switch (impl) {
            case UNARY -> UnaryExprHandler.INSTANCE.calculate(ctx, vars, unaryVisitor);
            case PRIMARY -> PrimaryExprHandler.INSTANCE.calculate(ctx, primaryVisitor);
        };
    }

    private static IUnaryExpr.Implementation getImplementation(Calc2Parser.UnaryExprContext ctx) {
        return HANDLERS.stream()
                .map((IUnaryExpr handler) -> handler.getImpl(ctx))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
