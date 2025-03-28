package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.util.Map;
import java.util.Optional;

public interface IStatefulExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<OCTX> handle(ICTX ctx, Snapshot snapshot);
}
