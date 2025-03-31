package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public class BracketTkn extends Tkn {
    private final boolean isOpenBracket;

    public BracketTkn(String originalRep, boolean isOpenBracket) {
        super(originalRep);
        this.isOpenBracket = isOpenBracket;
    }
}
