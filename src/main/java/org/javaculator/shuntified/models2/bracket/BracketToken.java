package org.javaculator.shuntified.models2.bracket;

import lombok.Getter;
import org.javaculator.shuntified.models2.Token;

@Getter
public class BracketToken extends Token {
    private final boolean isOpening;

    public BracketToken(String sign, boolean isOpening) {
        super(sign);
        this.isOpening = isOpening;
    }
}
