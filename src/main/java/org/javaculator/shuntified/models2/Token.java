package org.javaculator.shuntified.models2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Token {
    public enum Association {LEFT, RIGHT}

    private String sign;

    public <T> T castTo(Class<T> clazz) {
        return clazz.cast(this);
    }
}
