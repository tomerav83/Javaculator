package org.javaculator.antlr4.op.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.abstracts.impl.IdentifierCalcOp;

import java.util.Map;

public class PostIncOp extends IdentifierCalcOp<CalcParser.PostIncrementExprContext> {
    private PostIncOp(CalcParser.PostIncrementExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.PostIncrementExprContext ctx, Map<String, Integer> vars) {
        return new PostIncOp(ctx).calculate(vars);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        return vars.put(identifier, vars.get(identifier) + 1);
    }

    @Override
    protected String getOp() {
        throw new IllegalStateException("IdentifierExpr isn't operation oriented");
    }
}
