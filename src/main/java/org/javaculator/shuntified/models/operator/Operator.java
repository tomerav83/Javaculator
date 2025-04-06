package org.javaculator.shuntified.models.operator;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;

import java.util.Objects;


@Getter
public abstract class Operator extends Token {
    private final Association association;
    private final int precedence;

    protected Operator(String sign,
                       Association association,
                       int precedence) {
        super(sign);

        this.association = association;
        this.precedence = precedence;
    }

    public boolean isLeftAssociative() {
        return Objects.equals(association, Association.LEFT);
    }
}
