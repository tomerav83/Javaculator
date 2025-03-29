package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.antlr4.exceptions.UnknownOperatorException;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;

public class PostIncDecHandler implements IStatefulExprHandler<CalcParser.PostIncDecExprContext, BigDecimal> {
    public static final PostIncDecHandler INSTANCE = new PostIncDecHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.PostIncDecExprContext ctx, Snapshot snapshot) {
        BigDecimal current = getFromSnapshot(ctx, snapshot);
        String op = ctx.getChild(1).getText();

        return switch (op) {
            case "--" -> Optional.ofNullable(putAndGetPrevious(ctx, snapshot, BigDecimalSupport.dec(current)));
            case "++" -> Optional.ofNullable(putAndGetPrevious(ctx, snapshot, BigDecimalSupport.inc(current)));
            default ->  throw new UnknownOperatorException(op);
        };
    }
}
