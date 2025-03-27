package org.javaculator.antlr4.op.unary.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.unary.UnaryExprOp;

import java.util.Map;

public class PostDecOp extends UnaryExprOp<CalcParser.PostDecrementExprContext> {
    private PostDecOp(CalcParser.PostDecrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PostDecrementExprContext ctx, Map<String, Integer> vars) {
        return new PostDecOp(ctx).apply(vars);
    }

    @Override
    protected Integer apply(Map<String, Integer> vars) throws CalculationException {
        if (isIdentifierNotSetOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.put(identifier, vars.get(identifier) - 1);
    }
}
