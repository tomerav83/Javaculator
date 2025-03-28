package org.javaculator.antlr42po.handlers.primary;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.primary.impl.IdentifierExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.LiteralExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.ParenthesisExprHandler;
import org.javaculator.antlr42po.handlers.primary.impl.PreOpExprHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class PrimaryExprHandlers {
    private static final Set<IPrimaryExpr> HANDLERS = Set.of(
            IdentifierExprHandler.INSTANCE, LiteralExprHandler.INSTANCE,
            ParenthesisExprHandler.INSTANCE, PreOpExprHandler.INSTANCE);

    public static Integer handle(Calc2Parser.PrimaryExprContext ctx,
                                 Map<String, Integer> vars,
                                 Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        IPrimaryExpr.Implementation impl = getImplementation(ctx);

        return switch (impl) {
            case LITERAL -> LiteralExprHandler.INSTANCE.calculate(ctx);
            case PARENS -> ParenthesisExprHandler.INSTANCE.calculate(ctx, visitor);
            case PRE_OP -> PreOpExprHandler.INSTANCE.calculate(ctx, vars);
            case IDENTIFIER -> IdentifierExprHandler.INSTANCE.calculate(ctx, vars);
        };
    }

    private static IPrimaryExpr.Implementation getImplementation(Calc2Parser.PrimaryExprContext ctx) {
        return HANDLERS.stream()
                .map((IPrimaryExpr handler) -> handler.getImpl(ctx))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
