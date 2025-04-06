package org.javaculator.shuntified.parser.handlers.impl.op;

import org.javaculator.shuntified.exceptions.rpn.InvalidAssignOpException;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.variable.VariableToken;
import org.javaculator.shuntified.parser.handlers.RpnHandler;

import java.util.EmptyStackException;
import java.util.Stack;

public class AssignOpHandler implements RpnHandler<AssignOp> {
    public static final AssignOpHandler INSTANCE = new AssignOpHandler();

    @Override
    public void handleToken(AssignOp token, Stack<Token> operatorStack, Stack<Token> outputRPN) {
        try {
            if (outputRPN.peek() instanceof VariableToken) {
                operatorStack.push(token.setTargetVariable(outputRPN.pop().getSign()));
                return;
            }

            throw new InvalidAssignOpException(outputRPN.peek());
        } catch (EmptyStackException e) {
            throw new InvalidAssignOpException();
        }
    }
}
