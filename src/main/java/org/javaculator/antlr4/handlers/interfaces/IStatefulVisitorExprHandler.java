package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface IStatefulVisitorExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<BigDecimal> handle(ICTX ctx, Snapshot snapshot, Function<OCTX, BigDecimal> visitor);
}
