package org.javaculator.antlr4.op.unary.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.unary.UnaryExprOp;

import java.util.Map;

public class PostIncOp extends UnaryExprOp<CalcParser.PostIncrementExprContext> {
    private PostIncOp(CalcParser.PostIncrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PostIncrementExprContext ctx, Map<String, Integer> vars) {
        return new PostIncOp(ctx).apply(vars);
    }

    @Override
    protected Integer apply(Map<String, Integer> vars) throws CalculationException {
        if (!isIdentifierSetNonNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.put(identifier, vars.get(identifier) + 1);
    }
}
