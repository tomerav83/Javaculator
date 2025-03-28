package org.javaculator.antlr4.op.lr;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.impl.VisitorCalcOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.function.Function;

public class MulDivModOp extends VisitorCalcOp<CalcParser.MulDivModExprContext> {
    private MulDivModOp(CalcParser.MulDivModExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.MulDivModExprContext ctx,
                                 Function<CalcParser.ExpressionContext, Integer> visitor) {
        return new MulDivModOp(ctx).calculate(visitor);
    }

    @Override
    protected Integer calculate(Function<CalcParser.ExpressionContext, Integer> visitor) {
        Integer left = visitor.apply(ctx.getRuleContext(CalcParser.ExpressionContext.class, 0));
        Integer right = visitor.apply(ctx.getRuleContext(CalcParser.ExpressionContext.class, 1));
        String op = getOp();

        return switch (op) {
            case "*" -> left * right;
            case "/" -> left / right;
            case "%" -> left % right;
            default -> throw new RuntimeException("Unknown op: " + op);
        };
    }

    @Override
    protected String getOp() {
        return ctx.op.getText();
    }
}
