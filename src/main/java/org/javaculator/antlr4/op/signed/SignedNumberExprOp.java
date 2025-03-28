package org.javaculator.antlr4.op.signed;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.impl.LiteralCalcOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

public class SignedNumberExprOp extends LiteralCalcOp<CalcParser.SignedNumberExprContext> {
    protected SignedNumberExprOp(CalcParser.SignedNumberExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.SignedNumberExprContext ctx) {
        return new SignedNumberExprOp(ctx).calculate();
    }

    @Override
    protected Integer calculate() {
        Integer value = ParserCtxUtils.getLiteral(ctx);
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
