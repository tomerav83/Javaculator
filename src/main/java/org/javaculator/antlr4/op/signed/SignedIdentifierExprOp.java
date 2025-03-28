package org.javaculator.antlr4.op.signed;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.CalculationException.Type;
import org.javaculator.antlr4.op.abstracts.impl.IdentifierCalcOp;

import java.util.Map;

public class SignedIdentifierExprOp extends IdentifierCalcOp<CalcParser.SignedIdentifierExprContext> {
    private SignedIdentifierExprOp(CalcParser.SignedIdentifierExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.SignedIdentifierExprContext ctx, Map<String, Integer> vars) {
       return new SignedIdentifierExprOp(ctx).calculate(vars);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        Integer value = vars.get(identifier);
        String op = getOp();

        return switch (op) {
            case "+" -> value;
            case "-" -> -value;
            default -> throw new RuntimeException("Unknown op: " + op);
        };
    }

    @Override
    protected String getOp() {
        return ctx.op.getText();
    }
}
