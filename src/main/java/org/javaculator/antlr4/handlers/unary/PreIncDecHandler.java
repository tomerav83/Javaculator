package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;

public class PreIncDecHandler implements IStatefulExprHandler<CalcParser.PreIncDecExprContext, BigDecimal> {
    public static final PreIncDecHandler INSTANCE = new PreIncDecHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PreIncDecExprContext ctx, Snapshot snapshot) {
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
            case "--" -> Optional.of(
                    snapshot.putAndGetCurrent(identifier, BigDecimalSupport.dec(current)));
            case "++" -> Optional.of(
                    snapshot.putAndGetCurrent(identifier, BigDecimalSupport.inc(current)));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
