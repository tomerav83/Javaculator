package org.javaculator.antlr4.exceptions.impl;

import org.javaculator.antlr4.exceptions.JavaculatorException;

/**
 * Exception thrown when a user passes an invalid operation to the calculator.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate that the
 * calculation could not be performed because of an invalid operation provided by the user.
 * </p>
 */
public class InvalidCalculationException extends JavaculatorException {

    /**
     * Constructs a new {@link  InvalidCalculationException} with a detailed message and
     * a cause.
     *
     * @param template a string template describing the invalid operation that was passed.
     *                 This value will be formatted into the exception message.
     * @param e        the cause of this exception, which can provide additional context.
     */
    public InvalidCalculationException(String template, Throwable e) {
        super("User passed an invalid operation op=\"%s\" to calculator:".formatted(template), e);
    }
}
