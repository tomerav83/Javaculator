package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public abstract class OperatorTkn extends Tkn {
    public OperatorTkn(String originalRep) {
        super(originalRep);
    }

    public abstract Op getOperation();
}
