package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Optional;

public class IdentifierExprHandler implements IStatefulExprHandler<CalcParser.IdentifierExprContext, BigDecimal> {
    public static final IdentifierExprHandler INSTANCE = new IdentifierExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.IdentifierExprContext ctx, Snapshot snapsoht) {
        if (ctx.ID() == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(snapsoht.get(ctx.ID().getText()));
    }
}
