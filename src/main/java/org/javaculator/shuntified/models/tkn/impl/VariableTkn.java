package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public class VariableTkn extends Tkn {
    public VariableTkn(String originalRep) {
        super(originalRep);
    }
}
