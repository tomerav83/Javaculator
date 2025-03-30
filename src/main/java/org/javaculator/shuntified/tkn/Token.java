package org.javaculator.shuntified.tkn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Token {
    private final String input;

    @Override
    public int hashCode() {
        return this.input.hashCode();
    }

    @Override
    public String toString() {
        return "%s [input=\"%s\"]".formatted(this.getClass().getSimpleName(), input);
    }
}


