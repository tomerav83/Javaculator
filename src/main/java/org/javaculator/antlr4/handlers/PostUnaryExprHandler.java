package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class PostUnaryExprHandler implements IStatefulExprHandler<CalcParser.PostUnaryExprContext, BigDecimal> {
    public static final PostUnaryExprHandler INSTANCE = new PostUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PostUnaryExprContext ctx, Snapshot snapsoht) {
        if (ctx.ID() == null || ctx.getChildCount() != 2) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(1).getText();

        if (snapsoht.isMissingOrNull(identifier)) {
            return Optional.empty();
        }

        return switch (op) {
            case "--" -> Optional.ofNullable(
                    snapsoht.putAndGetPrevious(identifier, snapsoht.get(identifier).subtract(BigDecimal.ONE)));
            case "++" -> Optional.ofNullable(
                    snapsoht.putAndGetPrevious(identifier, snapsoht.get(identifier).add(BigDecimal.ONE)));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
