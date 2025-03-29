package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalcParser.ParenExprContext;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class ParenExprHandler implements IVisitorExprHandler<ParenExprContext, CalcParser.ExpressionContext> {
    public static final ParenExprHandler INSTANCE = new ParenExprHandler();

    @Override
    public Optional<BigDecimal> handle(ParenExprContext ctx, Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.expression()).map(visitor);
    }
}
