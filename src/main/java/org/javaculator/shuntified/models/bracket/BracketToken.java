package org.javaculator.shuntified.models.bracket;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.variable.VariableToken;

import java.util.Objects;

@Getter
public class BracketToken extends Token {
    private final boolean isOpening;

    public BracketToken(String sign, boolean isOpening) {
        super(sign);
        this.isOpening = isOpening;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BracketToken bracketToken)) {
            return false;
        }

        return Objects.equals(getSign(), bracketToken.getSign()) &&
                Objects.equals(isOpening(), bracketToken.isOpening());
    }
}
