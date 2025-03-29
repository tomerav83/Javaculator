package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.utils.BigDecimalSupport;

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

        if (snapsoht.isMissingOrNull(identifier)) {
            return Optional.empty();
        }

        BigDecimal current = snapsoht.get(identifier);
        String op = ctx.getChild(1).getText();

        return switch (op) {
            case "--" -> Optional.ofNullable(
                    snapsoht.putAndGetPrevious(identifier, BigDecimalSupport.dec(current)));
            case "++" -> Optional.ofNullable(
                    snapsoht.putAndGetPrevious(identifier, BigDecimalSupport.inc(current)));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
