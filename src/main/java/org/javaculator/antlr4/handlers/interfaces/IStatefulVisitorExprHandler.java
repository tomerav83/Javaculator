package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface IStatefulVisitorExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<BigDecimal> handle(ICTX ctx, Snapshot snapshot, Function<OCTX, BigDecimal> visitor);
    default BigDecimal getFromSnapshot(ICTX ctx, Snapshot snapshot) {
        return  snapshot.get(ctx.getToken(CalcParser.ID, 0).getText());
    }

    default BigDecimal putAndGetCurrent(ICTX ctx, Snapshot snapshot, BigDecimal value) {
        return  snapshot.putAndGetCurrent(ctx.getToken(CalcParser.ID, 0).getText(), value);
    }
}
