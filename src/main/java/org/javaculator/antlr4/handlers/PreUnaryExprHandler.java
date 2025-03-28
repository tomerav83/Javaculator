package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Optional;

public class PreUnaryExprHandler implements IStatefulExprHandler<CalcParser.PreUnaryExprContext, BigDecimal> {
    public static final PreUnaryExprHandler INSTANCE = new PreUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PreUnaryExprContext ctx, Snapshot snapshot) {
        if (ctx.ID() == null || ctx.getChildCount() != 2) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(0).getText();

        if (snapshot.isMissingOrNull(identifier)) {
            return Optional.empty();
        }

        return switch (op) {
            case "--" -> Optional.of(
                    snapshot.putAndGetCurrent(identifier, snapshot.get(identifier).subtract(BigDecimal.ONE)));
            case "++" -> Optional.of(
                    snapshot.putAndGetCurrent(identifier, snapshot.get(identifier).add(BigDecimal.ONE)));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
