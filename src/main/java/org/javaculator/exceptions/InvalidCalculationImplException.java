package org.javaculator.exceptions;

public class InvalidCalculationImplException extends RuntimeException {
    public InvalidCalculationImplException(String impl) {
        super("Invalid usage of calculate function variant for %s impl".formatted(impl));
    }
}
