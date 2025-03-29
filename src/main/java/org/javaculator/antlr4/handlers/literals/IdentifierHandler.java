package org.javaculator.antlr4.handlers.literals;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Optional;

public class IdentifierHandler implements IStatefulExprHandler<CalcParser.IdentifierContext, BigDecimal> {
    public static final IdentifierHandler INSTANCE = new IdentifierHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.IdentifierContext ctx, Snapshot snapsoht) {
        return Optional.ofNullable(getFromSnapshot(ctx, snapsoht));
    }
}
