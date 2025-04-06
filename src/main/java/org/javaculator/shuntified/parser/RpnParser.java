package org.javaculator.shuntified.parser;

import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.bracket.BracketToken;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.models.variable.VariableToken;
import org.javaculator.shuntified.parser.handlers.impl.op.AssignOpHandler;
import org.javaculator.shuntified.parser.handlers.impl.op.BinaryOpHandler;
import org.javaculator.shuntified.parser.handlers.impl.op.UnaryOpHandler;
import org.javaculator.shuntified.parser.handlers.impl.token.BracketTokenHandler;
import org.javaculator.shuntified.parser.handlers.impl.token.LiteralTokenHandler;
import org.javaculator.shuntified.parser.handlers.impl.token.VariableTokenHandler;

import java.util.List;
import java.util.Stack;

public class RpnParser {
    public static List<Token> parse(List<Token> tokens) {
        Stack<Token> outputRPN = new Stack<>();
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            if (token instanceof BinaryOp binaryOp) {
                BinaryOpHandler.INSTANCE.handleToken(binaryOp, operatorStack, outputRPN);
            } else if (token instanceof UnaryOp unaryOp) {
                UnaryOpHandler.INSTANCE.handleToken(unaryOp, operatorStack, outputRPN);
            } else if (token instanceof AssignOp assignOp) {
                AssignOpHandler.INSTANCE.handleToken(assignOp, operatorStack, outputRPN);
            } else if (token instanceof LiteralToken literalToken) {
                LiteralTokenHandler.INSTANCE.handleToken(literalToken, operatorStack, outputRPN);
            } else if (token instanceof VariableToken variableToken) {
                VariableTokenHandler.INSTANCE.handleToken(variableToken, operatorStack, outputRPN);
            } else if (token instanceof BracketToken bracketToken) {
                BracketTokenHandler.INSTANCE.handleToken(bracketToken, operatorStack, outputRPN);
            }
        }

        while (!operatorStack.empty()) {
            outputRPN.push(operatorStack.pop());
        }

        return outputRPN;
    }
}
