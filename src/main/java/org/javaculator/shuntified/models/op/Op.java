package org.javaculator.shuntified.models.op;

import lombok.Getter;
import org.javaculator.shuntified.models.tkn.impl.op.OpTkn;

@Getter
public abstract class Op {
    private final String symbol;

    protected Op(String symbol) {
        this.symbol = symbol;
    }

    public int getLength() {
        return symbol.length();
    }

    public abstract OpTkn getToken();
}
