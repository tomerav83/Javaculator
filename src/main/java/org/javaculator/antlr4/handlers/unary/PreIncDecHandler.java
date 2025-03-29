package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.antlr4.exceptions.UnknownOperatorException;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;

public class PreIncDecHandler implements IStatefulExprHandler<CalcParser.PreIncDecExprContext, BigDecimal> {
    public static final PreIncDecHandler INSTANCE = new PreIncDecHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PreIncDecExprContext ctx, Snapshot snapshot) {
        BigDecimal value = getFromSnapshot(ctx, snapshot);
        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "--" -> Optional.of(putAndGetCurrent(ctx, snapshot, BigDecimalSupport.dec(value)));
            case "++" -> Optional.of(putAndGetCurrent(ctx, snapshot, BigDecimalSupport.inc(value)));
            default -> throw new UnknownOperatorException(op);
        };
    }
}
