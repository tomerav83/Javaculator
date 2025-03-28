package org.javaculator.antlr4.op.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.abstracts.impl.IdentifierCalcOp;

import java.util.Map;

public class IdentifierOP extends IdentifierCalcOp<CalcParser.IdentifierContext> {
    private IdentifierOP(CalcParser.IdentifierContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.IdentifierContext ctx, Map<String, Integer> vars) {
        return new IdentifierOP(ctx).calculate(vars);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.get(identifier);
    }

    @Override
    protected String getOp() {
        throw new IllegalStateException("IdentifierExpr isn't operation oriented");
    }
}
