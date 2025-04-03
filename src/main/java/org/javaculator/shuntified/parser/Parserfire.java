package org.javaculator.shuntified.parser;

import org.javaculator.shuntified.models2.Token;
import org.javaculator.shuntified.models2.TokenUtils;
import org.javaculator.shuntified.models2.bracket.BracketToken;
import org.javaculator.shuntified.models2.op.impl.BinaryOp;
import org.javaculator.shuntified.models2.op.impl.UnaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parserfire {
    public static List<Token> parse(List<Token> equation) {
        List<Token> outputRPN = new ArrayList<>(); // Output RPN
        Stack<Token> operatorStack = new Stack<>();    // Used to temporarily hold operators

        // For each token in the equation
        for (Token token: equation) {
            switch (TokenUtils.getRpnOperationIndex(token)) {
                case 1 ->  processValue(token, outputRPN, operatorStack);
                case 2 ->  processBinaryOperator(token.castTo(BinaryOp.class), outputRPN, operatorStack);
                case 3 ->  processUnary(token.castTo(UnaryOperator.class), operatorStack);
                case 4 -> processBracket(token.castTo(BracketToken.class), outputRPN, operatorStack);
                case 5 -> operatorStack.push(token);
                default ->  throw new RuntimeException("Equation %s has token %s which is unsupported".formatted(equation, token));
            }
        }

        // Pop any remaining binary operators
        while (!operatorStack.empty()) {
            outputRPN.add(operatorStack.pop());
        }

        // Return RPN output
        return outputRPN;
    }

    private static void processUnary(UnaryOperator unaryOperator, Stack<Token> operatorStack) {
        if (unaryOperator.isNegate()) {
            operatorStack.push(unaryOperator);
            return;
        } else if (!operatorStack.isEmpty()) {
            operatorStack.push(unaryOperator);
            return;
        }

        throw new RuntimeException("missing operator before unary action " + unaryOperator.getSign());
    }


    /**
     * Processes a token that contains a value.
     * @param currentToken          The token currently being parsed.
     * @param outputRPN             The RPN output of the parser.
     * @param operatorStack         The current operator stack.
     */
    protected static void processValue(Token currentToken, List<Token> outputRPN, Stack<Token> operatorStack) {
        // Immediately add the token to the output
        outputRPN.add(currentToken);

        // Pop all of the unary operators at the top of the stack and add them to
        // the output
        while (!operatorStack.empty() && (operatorStack.peek() instanceof UnaryOperator)) {
            outputRPN.add(operatorStack.pop());
        }
    }

    /**
     * Processes a token that is a binary operator.
     * @param currentToken          The token currently being parsed.
     * @param outputRPN             The RPN output of the parser.
     * @param operatorStack         The current operator stack.
     */
    protected static void processBinaryOperator(BinaryOp currentToken,
                                                List<Token> outputRPN,
                                                Stack<Token> operatorStack) {
        while (!operatorStack.empty()) {
            // Get the operator at the top of the stack
            Token operator = operatorStack.peek();

            // As soon as a token that isn't a binary operator is met, stop
            if (!(operator instanceof BinaryOp binaryOp)) {
                break;
            }

            // Compare the precedence of the current operator being processed to the precedence of the operator on
            // the stack
            int diff = currentToken.getPrecedence() -
                    binaryOp.getPrecedence();

            // If the operator at the top of the stack has greater precedence, or they have equal precedence and the
            // current operator is left associative, pop the operator and add it to the stack
            // Otherwise, break, as we know there won't be any other operators in the stack satisfying the condition
            if (diff < 0 || (diff == 0 && currentToken.isLeftAssociative())) {
                outputRPN.add(operatorStack.pop());
            } else{
                break;
            }
        }

        // Push the current operator onto the stack
        operatorStack.push(currentToken);
    }

    /**
     * Processes a token that is a close bracket.
     * @param outputRPN             The RPN output of the parser.
     * @param operatorStack         The current operator stack.
     */
    protected static void processBracket(BracketToken bracket, List<Token> outputRPN, Stack<Token> operatorStack) {
        // When close bracket is found, add all the operators in the stack to the output RPN until the matching
        // open bracket is found
        if (bracket.isOpening()) {
            // Push immediately onto stack and process later
            operatorStack.push(bracket);
            return;
        }

        Token operator = null;
        while (!operatorStack.empty()) {
            // Get next operator
            operator = operatorStack.pop();

            if (operator instanceof BracketToken) {
                // As close brackets don't get added to the stack, we know this must be an open bracket
                // As we don't want to keep this open bracket, there is no need to re-add it to the stack
                break;
            }

            outputRPN.add(operator);
        }

        // Check last operator was an open bracket
        if (!(operator instanceof BracketToken)) {
            throw new RuntimeException("No matching open bracket found");
        }

        // Process all the unary operators at the top of the stack (as these apply to this bracket)
        while (!operatorStack.empty() &&
                (operatorStack.peek() instanceof UnaryOperator unaryOperator && unaryOperator.isNegate())) {
            outputRPN.add(operatorStack.pop());
        }
    }
}
