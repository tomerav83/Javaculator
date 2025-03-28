package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.util.Optional;

public class RootPrimaryExprHandler implements IExprHandler<CalcParser.RootPrimaryExprContext, CalcParser.PrimaryExprContext> {
    public static final RootPrimaryExprHandler INSTANCE = new RootPrimaryExprHandler();

    @Override
    public Optional<CalcParser.PrimaryExprContext> handle(CalcParser.RootPrimaryExprContext ctx) {
       return Optional.ofNullable(ctx.primaryExpr());
    }
}
