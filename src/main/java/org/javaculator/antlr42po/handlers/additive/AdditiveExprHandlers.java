package org.javaculator.antlr42po.handlers.additive;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.additive.impl.AdditiveExprHandler;
import org.javaculator.antlr42po.handlers.multiplicative.IMultiplicativeExpr;
import org.javaculator.antlr42po.handlers.multiplicative.impl.MultiplicativeExprHandler;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class AdditiveExprHandlers {
    private static final Set<IAdditiveExpr> HANDLERS = Set.of(AdditiveExprHandler.INSTANCE);

    public static Integer handle(Calc2Parser.AdditiveExprContext ctx,
                                 Function<Calc2Parser.MultiplicativeExprContext, Integer> visitor) {
        IAdditiveExpr.Implementation impl = getImplementation(ctx);

        return switch (impl) {
            case ADDITIVE -> AdditiveExprHandler.INSTANCE.calculate(ctx, visitor);
        };
    }

    private static IAdditiveExpr.Implementation getImplementation(Calc2Parser.AdditiveExprContext ctx) {
        return HANDLERS.stream()
                .map((IAdditiveExpr handler) -> handler.getImpl(ctx))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
