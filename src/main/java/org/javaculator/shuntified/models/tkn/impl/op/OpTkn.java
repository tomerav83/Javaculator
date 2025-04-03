package org.javaculator.shuntified.models.tkn.impl.op;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public abstract class OpTkn extends Tkn {
    public OpTkn(String originalRep) {
        super(originalRep);
    }

    public abstract Op getOperation();
}
