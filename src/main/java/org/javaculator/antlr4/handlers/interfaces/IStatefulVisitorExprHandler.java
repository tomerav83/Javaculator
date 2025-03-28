package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface IStatefulVisitorExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<Integer> handle(ICTX ctx, Map<String, Integer> state, Function<OCTX, Integer> visitor);
}
