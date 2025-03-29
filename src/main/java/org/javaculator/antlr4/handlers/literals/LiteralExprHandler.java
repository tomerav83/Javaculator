package org.javaculator.antlr4.handlers.literals;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class LiteralExprHandler implements IVisitorExprHandler<CalcParser.LiteralsExprContext, CalcParser.LiteralsContext> {
    public static final LiteralExprHandler INSTANCE = new LiteralExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.LiteralsExprContext ctx, Function<CalcParser.LiteralsContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.literals()).map(visitor);
    }
}
