package org.javaculator.shuntified.models.literal;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;

import java.util.Objects;
import java.util.Optional;

@Getter
public class LiteralToken extends Token {
    private final Double value;

    public LiteralToken(LiteralWrapper wrapper) {
        super(wrapper.original());
        this.value = Optional.ofNullable(wrapper.number())
                .map(Number::doubleValue)
                .orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LiteralToken literalToken)) {
            return false;
        }

        return Objects.equals(getSign(), literalToken.getSign()) &&
                Objects.equals(getValue(), literalToken.getValue());

    }
}
