package org.javaculator.exceptions;

public class InvalidCalculationException extends RuntimeException {
    public InvalidCalculationException(String template, Throwable e) {
        super("User passed an invalid operation op=\"%s\" to calculator:".formatted(template), e);
    }
}
