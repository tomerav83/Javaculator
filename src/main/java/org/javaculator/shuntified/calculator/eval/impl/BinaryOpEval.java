package org.javaculator.shuntified.calculator.eval.impl;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.Eval;
import org.javaculator.shuntified.exceptions.eval.UnmatchedPreconditions;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;

import java.util.Stack;
import java.util.function.BinaryOperator;

public class BinaryOpEval implements Eval<BinaryOp> {
    public static final BinaryOpEval INSTANCE = new BinaryOpEval();

    @Override
    public boolean passPreconditions(BinaryOp token, RollbackCache cache, Stack<Double> values) {
        if (values.size() < 2) {
            throw new UnmatchedPreconditions("Missing RHS operands for binary op: op=\"%s\""
                    .formatted(token.getSign()));
        }

        return true;
    }

    @Override
    public void evaluate(BinaryOp token, RollbackCache cache, Stack<Double> values) {
       if (passPreconditions(token, cache, values)) {
           switch (token.getSign()) {
               case "+" -> evaluate(values, Double::sum);
               case "-" -> evaluate(values, (o1, o2) -> o1 - o2);
               case "*" -> evaluate(values,  (o1, o2) -> o1 * o2);
               case "/" -> evaluate(values,  (o1, o2) -> o1 / o2);
               case "%" -> evaluate(values,  (o1, o2) -> o1 % o2);
               default -> throw new RuntimeException("unhandled operation: " + token.getSign());
           }
       }
    }

    public void evaluate(Stack<Double> values, BinaryOperator<Double> operation) {
        Double op2 = values.pop();
        Double op1 = values.pop();

        values.push(operation.apply(op1, op2));
    }
}
