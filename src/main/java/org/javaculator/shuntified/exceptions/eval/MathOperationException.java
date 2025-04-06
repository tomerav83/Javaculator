package org.javaculator.shuntified.exceptions.eval;

public class MathOperationException extends EvaluationException {
    public MathOperationException(Throwable e) {
        super("Encountered invalid mathematical operation: ", e);
    }
}
