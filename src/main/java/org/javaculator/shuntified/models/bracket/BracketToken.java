package org.javaculator.shuntified.models.bracket;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;

@Getter
public class BracketToken extends Token {
    private final boolean isOpening;

    public BracketToken(String sign, boolean isOpening) {
        super(sign);
        this.isOpening = isOpening;
    }
}
