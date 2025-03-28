package org.javaculator.antlr42po.exceptions;

import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;

public class MissingOrNullIdentifierException extends RuntimeException {
    public MissingOrNullIdentifierException(String identifier) {
        super("Identifier %s is either not defined or null".formatted(identifier));
    }
}
