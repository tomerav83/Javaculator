package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class SingletonExprHandler implements IVisitorExprHandler<CalcParser.SingletonExprContext, CalcParser.AdditiveContext> {
    public static final SingletonExprHandler INSTANCE = new SingletonExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.SingletonExprContext ctx, Function<CalcParser.AdditiveContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.additive()).map(visitor);
    }
}
