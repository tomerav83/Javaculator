package org.javaculator.antlr4.op.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.abstracts.impl.IdentifierCalcOp;

import java.util.Map;

public class PreDecOp extends IdentifierCalcOp<CalcParser.PreDecrementExprContext> {
    private PreDecOp(CalcParser.PreDecrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PreDecrementExprContext ctx, Map<String, Integer> vars) {
        return new PreDecOp(ctx).calculate(vars);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.merge(identifier, -1, Integer::sum);
    }

    @Override
    protected String getOp() {
        throw new IllegalStateException("IdentifierExpr isn't operation oriented");
    }
}
