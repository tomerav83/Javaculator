package org.javaculator.shuntified.calculator.eval.impl;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.Eval;
import org.javaculator.shuntified.models.literal.LiteralToken;

import java.util.Stack;

public class LiteralTokenEval implements Eval<LiteralToken> {
    public static final LiteralTokenEval INSTANCE = new LiteralTokenEval();

    @Override
    public void evaluate(LiteralToken token, RollbackCache cache, Stack<Double> values) {
        values.push(token.getValue());
    }
}
