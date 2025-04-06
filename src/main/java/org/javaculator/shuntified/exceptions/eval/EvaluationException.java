package org.javaculator.shuntified.exceptions.eval;

public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super("Evaluation exception: " + message);
    }

    public EvaluationException(String message, Throwable e) {
        super("Evaluation exception: " + message, e);
    }
}
