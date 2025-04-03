package org.javaculator.shuntified.models2.val;

import lombok.Getter;
import org.javaculator.shuntified.models.tkn.Tkn;
import org.javaculator.shuntified.models2.Token;

@Getter
public class ValueToken extends Token {
    private final Double value;

    public ValueToken(String sign) {
        super(sign);
        this.value = convert(sign.replace("_", ""));
    }

    private Double convert(String sign) {
        if (sign.startsWith("0x") || sign.startsWith("0X")) {
            return (double) Long.decode(sign);
        } else if (sign.startsWith("0b") || sign.startsWith("0B")) {
            return (double) Long.parseLong(sign.substring(2), 2);
        } else if (sign.endsWith("l") || sign.endsWith("L")) {
            return Double.parseDouble(sign.replace("l", "")
                    .replace("L", ""));
        } else {
            return Double.parseDouble(sign);
        }
    }
}
