package org.javaculator.shuntified.exceptions.rpn;

public class RpnParsingException extends RuntimeException {
    public RpnParsingException(String message) {
        super("Rpn processing exception: %s".formatted(message));
    }
}
