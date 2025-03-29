package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface IStatefulExprHandler<ICTX extends ParserRuleContext, OCTX> {
    Optional<OCTX> handle(ICTX ctx, Snapshot snapshot);
    default BigDecimal getFromSnapshot(ICTX ctx, Snapshot snapshot) {
        return  snapshot.get(ctx.getToken(CalcParser.ID, 0).getText());
    }

    default BigDecimal putAndGetPrevious(ICTX ctx, Snapshot snapshot, BigDecimal value) {
        return  snapshot.putAndGetPrevious(ctx.getToken(CalcParser.ID, 0).getText(), value);
    }

    default BigDecimal putAndGetCurrent(ICTX ctx, Snapshot snapshot, BigDecimal value) {
        return  snapshot.putAndGetCurrent(ctx.getToken(CalcParser.ID, 0).getText(), value);
    }
}
