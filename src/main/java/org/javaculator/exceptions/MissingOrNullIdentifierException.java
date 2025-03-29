package org.javaculator.exceptions;

public class MissingOrNullIdentifierException extends RuntimeException {
    public MissingOrNullIdentifierException(String identifier) {
        super("Identifier %s is either not defined or null".formatted(identifier));
    }
}
