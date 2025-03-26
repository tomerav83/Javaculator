package org.javaculator.antlr4;

public class CalculationException extends RuntimeException{

    public CalculationException(Type type, String id) {
        super(type.prompt.formatted(id));
    }

    public enum Type {
        MISSING_IDENTIFIER("Missing identifier %s defined in calculation, please define it before attempting to use it");

        private final String prompt;

        Type(String prompt) {
            this.prompt = prompt;
        }
    }
}
