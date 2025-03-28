package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public interface IVisitorExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<BigDecimal> handle(ICTX ctx, Function<OCTX, BigDecimal> visitor);
}
