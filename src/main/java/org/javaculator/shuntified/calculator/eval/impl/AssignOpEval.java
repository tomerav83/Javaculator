package org.javaculator.shuntified.calculator.eval.impl;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.Eval;
import org.javaculator.shuntified.exceptions.eval.UnmatchedPreconditions;
import org.javaculator.shuntified.models.operator.impl.AssignOp;

import java.util.Stack;

public class AssignOpEval implements Eval<AssignOp> {
    public static final AssignOpEval INSTANCE = new AssignOpEval();

    @Override
    public boolean passPreconditions(AssignOp token, RollbackCache cache, Stack<Double> values) {
        if (values.isEmpty()) {
            throw new UnmatchedPreconditions("Missing RHS calculated value: op=\"%s\" targe=%s"
                    .formatted(token.getSign(), token.getTargetVariable()));
        }

        return true;
    }

    @Override
    @SuppressWarnings("all")
    public void evaluate(AssignOp token, RollbackCache cache, Stack<Double> values) {
        if (passPreconditions(token, cache, values)) {
            String variable = token.getTargetVariable();

            Double result = switch (token.getSign()) {
                case "=" -> cache.putAndGetCurrent(variable, values.pop());
                case "+=" -> cache.putAndGetCurrent(variable, values.pop(), (lhs, rhs) -> lhs + rhs);
                case "-=" -> cache.putAndGetCurrent(variable, values.pop(), (lhs, rhs) -> lhs - rhs);
                case "*=" -> cache.putAndGetCurrent(variable, values.pop(), (lhs, rhs) -> lhs * rhs);
                case "/=" -> cache.putAndGetCurrent(variable, values.pop(), (lhs, rhs) -> lhs / rhs);
                case "%=" -> cache.putAndGetCurrent(variable, values.pop(), (lhs, rhs) -> lhs % rhs);
                default -> throw new RuntimeException("un ; assignment operation");
            };

            values.push(result);
        }
    }
}
