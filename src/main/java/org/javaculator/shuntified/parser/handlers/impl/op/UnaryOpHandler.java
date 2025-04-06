package org.javaculator.shuntified.parser.handlers.impl.op;

import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.exceptions.rpn.InvalidUnaryOpException;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.Stack;

public class UnaryOpHandler implements RpnHandler<UnaryOp> {
    public static final UnaryOpHandler INSTANCE = new UnaryOpHandler();

    @Override
    public void handleToken(UnaryOp token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        if (token.isNegate()) {
            operatorStack.push(token);
            return;
        } else if (!operatorStack.isEmpty()) {
            operatorStack.push(token);
            return;
        }

        throw new InvalidUnaryOpException(token);
    }
}
