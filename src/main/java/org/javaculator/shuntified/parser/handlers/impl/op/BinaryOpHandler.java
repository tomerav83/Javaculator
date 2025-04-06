package org.javaculator.shuntified.parser.handlers.impl.op;

import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.Stack;

public class BinaryOpHandler implements RpnHandler<BinaryOp> {
    public static final BinaryOpHandler INSTANCE = new BinaryOpHandler();

    @Override
    public void handleToken(BinaryOp token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        while (!operatorStack.empty()) {
            Token operator = operatorStack.peek();

            if (!(operator instanceof BinaryOp binaryOp)) {
                break;
            }

            int diff = token.getPrecedence() - binaryOp.getPrecedence();

            if (diff < 0 || (diff == 0 && token.isLeftAssociative())) {
                outputRPN.push(operatorStack.pop());
            } else{
                break;
            }
        }

        operatorStack.push(token);
    }
}
