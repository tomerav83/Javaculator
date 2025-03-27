package org.javaculator.antlr4.op.lr.impl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.lr.LRExprOp;

import java.util.function.Function;

public class MulDivModOp extends LRExprOp<CalcParser.MulDivModExprContext> {
    private MulDivModOp(CalcParser.MulDivModExprContext ctx, Function<ParseTree, Integer> visitor) {
        super(ctx, ctx.op, visitor);
    }

    public static Integer handle(CalcParser.MulDivModExprContext ctx, Function<ParseTree, Integer> visitor) {
        return new MulDivModOp(ctx, visitor).calculate();
    }

    @Override
    protected Integer calculate() {
        return switch (op) {
            case "*" -> left * right;
            case "/" -> left / right;
            case "%" -> left % right;
            default -> throw new RuntimeException("Unknown op: " + op);
        };
    }
}
