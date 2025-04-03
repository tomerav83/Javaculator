package org.javaculator.shuntified.models.op.impl;

import lombok.Getter;
import org.javaculator.shuntified.cache.CachingOperation;
import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.impl.op.AssignOpTkn;
import org.javaculator.shuntified.models.tkn.impl.op.OpTkn;

@Getter
public class AssignOp extends Op {
    private final CachingOperation<String, Double, RollbackCache, Double> action;

    public AssignOp(String symbol, CachingOperation<String, Double, RollbackCache, Double> action) {
        super(symbol);

        this.action = action;
    }


    @Override
    public OpTkn getToken() {
        return new AssignOpTkn(getSymbol(), this);
    }
}
