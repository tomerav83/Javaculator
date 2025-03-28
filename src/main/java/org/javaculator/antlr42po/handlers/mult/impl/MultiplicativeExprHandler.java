package org.javaculator.antlr42po.handlers.mult.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.mult.IMultiplicativeExpr;

import java.util.function.Function;

public class MultiplicativeExprHandler implements IMultiplicativeExpr {
    public static final MultiplicativeExprHandler INSTANCE = new MultiplicativeExprHandler();


    @Override
    public Implementation getImpl(Calc2Parser.MultiplicativeExprContext ctx) {
       if (ctx.unaryExpr() != null) {
           return Implementation.MULTIPLICATIVE;
       }

       return null;
    }

    @Override
    public Integer calculate(Calc2Parser.MultiplicativeExprContext ctx, Function<Calc2Parser.UnaryExprContext, Integer> visitor) {
        int result = visitor.apply(ctx.unaryExpr(0));

        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            Integer rhs = visitor.apply(ctx.unaryExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();


            result = switch (op) {
                case "*" -> result * rhs;
                case "/" -> result / rhs;
                case "%" -> result % rhs;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return result;
    }
}
