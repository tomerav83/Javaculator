package org.javaculator.shuntified.models.variable;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;

@Getter
public class VariableToken extends Token {
    public VariableToken(String sign) {
        super(sign);
    }
}
