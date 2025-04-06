package org.javaculator.shuntified.models.variable;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;

import java.util.Objects;

@Getter
public class VariableToken extends Token {
    public VariableToken(String sign) {
        super(sign);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VariableToken variableToken)) {
            return false;
        }

        return Objects.equals(getSign(), variableToken.getSign());
    }
}
