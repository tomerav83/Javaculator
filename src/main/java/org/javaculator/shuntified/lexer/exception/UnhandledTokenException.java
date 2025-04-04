package org.javaculator.shuntified.lexer.exception;

public class UnhandledTokenException extends Exception {

    public UnhandledTokenException(String input, int position) {
        super("""
              Could not match any token:
                    input=%s
                          %s
              """
                .formatted(input, "%s%s".formatted( " ".repeat(position), "^")));
    }
}
