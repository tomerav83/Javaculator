package org.javaculator.shuntified.parser.handlers.impl.token;

import org.javaculator.shuntified.exceptions.rpn.InvalidBracketsException;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.bracket.BracketToken;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.Stack;

public class BracketTokenHandler implements RpnHandler<BracketToken> {
    public static final BracketTokenHandler INSTANCE = new BracketTokenHandler();

    @Override
    public void handleToken(BracketToken token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        if (token.isOpening()) {
            operatorStack.push(token);
            return;
        }

        Token operator = null;
        while (!operatorStack.empty()) {
            operator = operatorStack.pop();

            if (operator instanceof BracketToken) {
                break;
            }

            outputRPN.push(operator);
        }

        if (!(operator instanceof BracketToken)) {
            throw new InvalidBracketsException();
        }

        while (!operatorStack.empty() &&
                (operatorStack.peek() instanceof UnaryOp unaryOp && unaryOp.isNegate())) {
            outputRPN.push(operatorStack.pop());
        }
    }
}
