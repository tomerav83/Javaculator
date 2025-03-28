package org.javaculator.antlr42po.handlers.additive.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.additive.IAdditiveExpr;
import org.javaculator.antlr42po.handlers.multiplicative.IMultiplicativeExpr;

import java.util.function.Function;

public class AdditiveExprHandler implements IAdditiveExpr {
    public static final AdditiveExprHandler INSTANCE = new AdditiveExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.AdditiveExprContext ctx) {
       if (ctx.multiplicativeExpr() != null) {
           return Implementation.ADDITIVE;
       }

       return null;
    }

    @Override
    public Integer calculate(Calc2Parser.AdditiveExprContext ctx, Function<Calc2Parser.MultiplicativeExprContext, Integer> visitor) {
        int result = visitor.apply(ctx.multiplicativeExpr(0));

        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            Integer rhs = visitor.apply(ctx.multiplicativeExpr(i));
            String op = ctx.getChild(2 * i - 1).getText(); // Operator is at odd positions.

            result = switch (op) {
                case "+" -> result + rhs;
                case "-" -> result - rhs;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return result;
    }
}
