package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.util.Optional;
import java.util.function.Function;

public class RootAddExprHandler implements IVisitorExprHandler<CalcParser.RootAddExprContext, CalcParser.AdditiveExprContext> {
    public static final RootAddExprHandler INSTANCE = new RootAddExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.RootAddExprContext ctx, Function<CalcParser.AdditiveExprContext, Integer> visitor) {
        return Optional.ofNullable(ctx.additiveExpr()).map(visitor);
    }
}
