package org.javaculator.antlr4.op.unary.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.unary.UnaryExprOp;

import java.util.Map;

public class IdentifierOP extends UnaryExprOp<CalcParser.IdentifierContext> {
    private IdentifierOP(CalcParser.IdentifierContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.IdentifierContext ctx, Map<String, Integer> vars) {
        return new IdentifierOP(ctx).apply(vars);
    }

    @Override
    protected Integer apply(Map<String, Integer> vars) throws CalculationException {
        if (isIdentifierNotSetOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.get(identifier);
    }
}
