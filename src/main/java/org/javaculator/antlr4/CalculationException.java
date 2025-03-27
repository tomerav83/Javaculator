package org.javaculator.antlr4;

public class CalculationException extends RuntimeException {

    public CalculationException(Type type, String id) {
        super(type.prompt.formatted(id));
    }

    public enum Type {
        MISSING_OR_NULL_IDENTIFIER("Missing or null identifier \"%s\" defined in calculation");

        private final String prompt;

        Type(String prompt) {
            this.prompt = prompt;
        }
    }
}
