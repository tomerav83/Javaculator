package org.javaculator.shuntified.calculator;

import lombok.Getter;
import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.eval.impl.AssignOpEval;
import org.javaculator.shuntified.calculator.eval.impl.BinaryOpEval;
import org.javaculator.shuntified.calculator.eval.impl.LiteralTokenEval;
import org.javaculator.shuntified.calculator.eval.impl.UnaryOpEval;
import org.javaculator.shuntified.calculator.eval.impl.VariableTokenEval;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.models.variable.VariableToken;

import java.util.List;
import java.util.Stack;

@Getter
public class Calculator {
    private final RollbackCache cache = new RollbackCache();

    public void evaluate(List<Token> equation)  {
        Stack<Double> values = new Stack<>();

        for (Token token : equation) {
            if (token instanceof BinaryOp binaryOp) {
                BinaryOpEval.INSTANCE.evaluate(binaryOp, cache, values);
            } else if (token instanceof UnaryOp unaryOp) {
                UnaryOpEval.INSTANCE.evaluate(unaryOp, cache, values);
            } else if (token instanceof LiteralToken literalToken) {
                LiteralTokenEval.INSTANCE.evaluate(literalToken, cache, values);
            } else if (token instanceof VariableToken variableTkn) {
                VariableTokenEval.INSTANCE.evaluate(variableTkn, cache, values);
            } else if (token instanceof AssignOp assignOp) {
                AssignOpEval.INSTANCE.evaluate(assignOp, cache, values);
            } else {
                throw new RuntimeException("Equation could not be calculated: ");
            }
        }
    }

    public void takeSnapshot() {
        cache.takeSnapshot();
    }

    public void rollback() {
        cache.rollback();
    }

    public void clear() {
        cache.clear();
    }
}
