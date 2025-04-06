package org.javaculator.shuntified.exceptions.eval;

public class UnmatchedPreconditions extends EvaluationException {
    public UnmatchedPreconditions(String message) {
        super("Unmatched precondition: %s".formatted(message));
    }
}
