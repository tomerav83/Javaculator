package org.javaculator.shuntified.parser.handlers;

import org.javaculator.shuntified.models.Token;

import java.util.Stack;

public interface RpnHandler<T extends Token> {
    void handleToken(T token, Stack<Token> operatorStack, Stack<Token> outputRPN);
}
