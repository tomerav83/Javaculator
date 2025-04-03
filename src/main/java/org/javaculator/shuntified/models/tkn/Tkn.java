package org.javaculator.shuntified.models.tkn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Tkn {
    private final String originalRep;

    public int getLength() {
        return originalRep.length();
    }

    @Override
    public String toString() {
        return "%s [Original representation=\"%s\"]".formatted(getClass().getSimpleName(), originalRep);
    }
}
