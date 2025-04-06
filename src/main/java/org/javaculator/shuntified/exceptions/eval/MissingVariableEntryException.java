package org.javaculator.shuntified.exceptions.eval;

public class MissingVariableEntryException extends EvaluationException {
    public MissingVariableEntryException(String key) {
        super("Missing value for variable %s".formatted(key));
    }
}
