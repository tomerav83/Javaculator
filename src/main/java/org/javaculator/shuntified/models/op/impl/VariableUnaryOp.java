package org.javaculator.shuntified.models.op.impl;

import lombok.Getter;
import org.javaculator.shuntified.cache.CachingOperation;
import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.impl.op.OpTkn;
import org.javaculator.shuntified.models.tkn.impl.op.VariableUnaryOpTkn;

@Getter
public class VariableUnaryOp extends Op {
    public static final String INC_REPLACED_SYMBOL = "[inc]";
    public static final String DEC_REPLACED_SYMBOL = "[dec]";

    private final CachingOperation<String, Boolean, RollbackCache, Double> action;

    public VariableUnaryOp(String symbol, CachingOperation<String, Boolean, RollbackCache, Double> action) {
        super(symbol);

        this.action = action;
    }


    @Override
    public OpTkn getToken() {
        return new VariableUnaryOpTkn(getSymbol(), this);
    }
}
