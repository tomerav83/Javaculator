package org.javaculator.shuntified.calculator.eval;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.models.Token;

import java.util.Stack;

public interface Eval<T extends Token> {
    default boolean passPreconditions(T token, RollbackCache cache, Stack<Double> values) {
        return true;
    }
    void evaluate(T token, RollbackCache cache, Stack<Double> values);
}
