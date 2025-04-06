package org.javaculator.shuntified.calculator.eval.impl;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.Eval;
import org.javaculator.shuntified.exceptions.eval.MathOperationException;
import org.javaculator.shuntified.exceptions.eval.UnmatchedPreconditions;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;

import java.util.Stack;

public class UnaryOpEval implements Eval<UnaryOp> {
    public static final UnaryOpEval INSTANCE = new UnaryOpEval();

    @Override
    public boolean passPreconditions(UnaryOp token, RollbackCache cache, Stack<Double> values) {
        if (token.isNegate() && values.isEmpty()) {
            throw new UnmatchedPreconditions("Missing RHS operand for unary negate op");
        }

        return true;
    }

    @Override
    public void evaluate(UnaryOp token, RollbackCache cache, Stack<Double> values) {
        if (passPreconditions(token, cache, values)) {
            values.push(token.isNegate() ? handleNegate(values) : handlePrePost(cache, token.getSign()));
        }
    }

    @SuppressWarnings("all")
    private Double handlePrePost(RollbackCache cache, String sign) {
        String operation = sign.substring(0, sign.indexOf('('));
        String variable = sign.substring(sign.indexOf('(') + 1, sign.indexOf(')'));

        return switch (operation) {
            case "preinc" -> cache.putAndGetCurrent(variable, 1D, (lhs, rhs) -> lhs + rhs);
            case "predec" -> cache.putAndGetCurrent(variable, 1D, (lhs, rhs) -> lhs - rhs);
            case "postinc" -> cache.putAndGetPrevious(variable, 1D, (lhs, rhs) -> lhs + rhs);
            case "postdec" -> cache.putAndGetPrevious(variable, 1D, (lhs, rhs) -> lhs - rhs);
            default -> throw new RuntimeException("Unhandled unary operation: " + sign);
        };
    }

    private Double handleNegate(Stack<Double> values) {
        try {
            return -values.pop();
        } catch (Exception e) {
            throw new MathOperationException(e);
        }
    }
}
