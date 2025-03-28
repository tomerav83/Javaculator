package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalcParser.ParenExprContext;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.util.Optional;

public class ParenExprHandler implements IExprHandler<ParenExprContext, CalcParser.ExpressionContext> {
    public static final ParenExprHandler INSTANCE = new ParenExprHandler();

    public Optional<CalcParser.ExpressionContext> handle(ParenExprContext ctx) {
        return Optional.ofNullable(ctx.expression());
    }
}
