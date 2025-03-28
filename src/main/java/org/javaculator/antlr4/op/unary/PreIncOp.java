package org.javaculator.antlr4.op.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.abstracts.impl.IdentifierCalcOp;

import java.util.Map;

public class PreIncOp extends IdentifierCalcOp<CalcParser.PreIncrementExprContext> {
    private PreIncOp(CalcParser.PreIncrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PreIncrementExprContext ctx, Map<String, Integer> vars) {
        return new PreIncOp(ctx).calculate(vars);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.merge(identifier, 1, Integer::sum);
    }

    @Override
    protected String getOp() {
        throw new IllegalStateException("IdentifierExpr isn't operation oriented");
    }
}
