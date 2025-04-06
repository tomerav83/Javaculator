package org.javaculator.shuntified.calculator.eval.impl;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.Eval;
import org.javaculator.shuntified.models.variable.VariableToken;

import java.util.Stack;

public class VariableTokenEval implements Eval<VariableToken> {
    public static final VariableTokenEval INSTANCE = new VariableTokenEval();

    @Override
    public void evaluate(VariableToken token, RollbackCache cache, Stack<Double> values) {
        values.push(cache.get(token.getSign()));
    }
}
