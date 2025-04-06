package org.javaculator.shuntified.parser.handlers.impl.token;

import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.models.variable.VariableToken;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.Stack;

public class VariableTokenHandler implements RpnHandler<VariableToken> {
    public static final VariableTokenHandler INSTANCE = new VariableTokenHandler();

    @Override
    public void handleToken(VariableToken token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        outputRPN.push(token);

        while (!operatorStack.empty() && (operatorStack.peek() instanceof UnaryOp)) {
            outputRPN.push(operatorStack.pop());
        }
    }
}
