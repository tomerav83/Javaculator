package org.javaculator.antlr42po.handlers.multiplicative;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.multiplicative.impl.MultiplicativeExprHandler;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class MultiplicativeExprHandlers {
    private static final Set<IMultiplicativeExpr> HANDLERS = Set.of(MultiplicativeExprHandler.INSTANCE);

    public static Integer handle(Calc2Parser.MultiplicativeExprContext ctx,
                                 Function<Calc2Parser.UnaryExprContext, Integer> visitor) {
        IMultiplicativeExpr.Implementation impl = getImplementation(ctx);

        return switch (impl) {
            case MULTIPLICATIVE -> MultiplicativeExprHandler.INSTANCE.calculate(ctx, visitor);
        };
    }

    private static IMultiplicativeExpr.Implementation getImplementation(Calc2Parser.MultiplicativeExprContext ctx) {
        return HANDLERS.stream()
                .map((IMultiplicativeExpr handler) -> handler.getImpl(ctx))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
