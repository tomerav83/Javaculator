package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;
import java.util.function.Function;

public interface IVisitorExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<Integer> handle(ICTX ctx, Function<OCTX, Integer> visitor);
}
