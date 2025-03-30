package org.javaculator.shuntified.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.tkn.Token;

import java.util.Objects;

@Getter
public class ConstantToken extends Token {
    private final Double value;

    private ConstantToken(String token, Double value) {
        super(token);
        this.value = value;
    }

    public static ConstantToken create(String token) {
        return new ConstantToken(token, Double.parseDouble(token));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstantToken constantToken) {
            return Objects.equals(getInput(), constantToken.getInput()) &&
                    Objects.equals(getValue(), constantToken.getValue());
        }

        return false;
    }
}
