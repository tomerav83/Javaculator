package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class RootAddExprHandler implements IVisitorExprHandler<CalcParser.RootAddExprContext, CalcParser.AdditiveExprContext> {
    public static final RootAddExprHandler INSTANCE = new RootAddExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.RootAddExprContext ctx, Function<CalcParser.AdditiveExprContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.additiveExpr()).map(visitor);
    }
}
