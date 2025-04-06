package org.javaculator.shuntified.parser.handlers.impl.token;

import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.Stack;

public class LiteralTokenHandler implements RpnHandler<LiteralToken> {
    public static final LiteralTokenHandler INSTANCE = new LiteralTokenHandler();

    @Override
    public void handleToken(LiteralToken token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        outputRPN.push(token);

        while (!operatorStack.empty() && (operatorStack.peek() instanceof UnaryOp)) {
            outputRPN.push(operatorStack.pop());
        }
    }
}
