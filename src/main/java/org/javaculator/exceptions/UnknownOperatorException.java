package org.javaculator.exceptions;

public class UnknownOperatorException extends RuntimeException {
    public UnknownOperatorException(String operator) {
        super("User passed an unknown operator op=\"%s\" to calculator:".formatted(operator));
    }
}
