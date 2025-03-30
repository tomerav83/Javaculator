package org.javaculator.antlr4.exceptions;

public class JavaculatorException extends RuntimeException {
    public JavaculatorException(String message) {
        super(message);
    }

    public JavaculatorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
