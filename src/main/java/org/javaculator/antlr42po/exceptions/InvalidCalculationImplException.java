package org.javaculator.antlr42po.exceptions;

import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;

public class InvalidCalculationImplException extends RuntimeException {
    public InvalidCalculationImplException(IPrimaryExpr.Implementation impl) {
        super("Invalid usage of calculate function variant for %s impl".formatted(impl));
    }
}
