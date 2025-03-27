package org.javaculator.antlr4.op.unary.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.unary.UnaryExprOp;

import java.util.Map;

public class PreDecOp extends UnaryExprOp<CalcParser.PreDecrementExprContext> {
    private PreDecOp(CalcParser.PreDecrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PreDecrementExprContext ctx, Map<String, Integer> vars) {
        return new PreDecOp(ctx).apply(vars);
    }

    @Override
    protected Integer apply(Map<String, Integer> vars) throws CalculationException {
        if (!isIdentifierSetNonNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.merge(identifier, -1, Integer::sum);
    }
}
