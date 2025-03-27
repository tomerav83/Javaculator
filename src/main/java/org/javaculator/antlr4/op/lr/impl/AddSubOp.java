package org.javaculator.antlr4.op.lr.impl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.lr.LRExprOp;

import java.util.function.Function;

public class AddSubOp extends LRExprOp<CalcParser.AddSubExprContext> {
    private AddSubOp(CalcParser.AddSubExprContext ctx, Function<ParseTree, Integer> visitor) {
        super(ctx, ctx.op, visitor);
    }

    public static Integer handle(CalcParser.AddSubExprContext ctx, Function<ParseTree, Integer> visitor) {
        return new AddSubOp(ctx, visitor).calculate();
    }

    @Override
    protected Integer calculate() {
        return switch (op) {
            case "+" -> left + right;
            case "-" -> left - right;
            default -> throw new RuntimeException("Unknown op: " + op);
        };
    }
}
