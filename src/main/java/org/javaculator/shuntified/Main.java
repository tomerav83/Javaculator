package org.javaculator.shuntified;

import org.javaculator.shuntified.lexer.Lexified;

public class Main {
    public static void main(String[] args) {
        String input = "--i + _a* / (2 + k) + 12e2 + 12e-2 + 0xFF + 12L + 12.0F";
        Lexified lexified = new Lexified(input);
        System.out.println(lexified.tokenize());
    }
}
