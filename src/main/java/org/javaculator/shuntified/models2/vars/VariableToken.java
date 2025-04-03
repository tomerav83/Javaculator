package org.javaculator.shuntified.models2.vars;

import lombok.Getter;
import org.javaculator.shuntified.models2.Token;

@Getter
public class VariableToken extends Token {
    public VariableToken(String sign) {
        super(sign);
    }
}
