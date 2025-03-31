package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public class ConstantTkn extends Tkn {
    private final Double value;

    public ConstantTkn(String originalRep) {
        super(originalRep);
        this.value = convert(originalRep.replace("_", ""));
    }

    private Double convert(String originalRep) {
        if (originalRep.startsWith("0x") || originalRep.startsWith("0X")) {
            return (double) Long.decode(originalRep);
        } else if (originalRep.startsWith("0b") || originalRep.startsWith("0B")) {
            return (double) Long.parseLong(originalRep.substring(2), 2);
        } else if (originalRep.endsWith("l") || originalRep.endsWith("L")) {
            return Double.parseDouble(originalRep.replace("l", "")
                    .replace("L", "l"));
        } else {
            return Double.parseDouble(originalRep);
        }
    }
}
