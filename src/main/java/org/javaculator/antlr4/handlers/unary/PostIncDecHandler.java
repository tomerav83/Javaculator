package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;

public class PostIncDecHandler implements IStatefulExprHandler<CalcParser.PostIncDecExprContext, BigDecimal> {
    public static final PostIncDecHandler INSTANCE = new PostIncDecHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PostIncDecExprContext ctx, Snapshot snapshot) {
        if (ctx.ID() == null || ctx.getChildCount() != 2) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();

        if (snapshot.isMissingOrNull(identifier)) {
            return Optional.empty();
        }

        BigDecimal current = snapshot.get(identifier);
        String op = ctx.getChild(1).getText();

        return switch (op) {
            case "--" -> Optional.ofNullable(
                    snapshot.putAndGetPrevious(identifier, BigDecimalSupport.dec(current)));
            case "++" -> Optional.ofNullable(
                    snapshot.putAndGetPrevious(identifier, BigDecimalSupport.inc(current)));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
