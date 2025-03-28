package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public interface IExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<OCTX> handle(ICTX ctx);
}
