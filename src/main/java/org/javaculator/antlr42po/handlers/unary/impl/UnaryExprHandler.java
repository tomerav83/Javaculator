package org.javaculator.antlr42po.handlers.unary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.handlers.primary.impl.IdentifierExprHandler;
import org.javaculator.antlr42po.handlers.unary.IUnaryExpr;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public class UnaryExprHandler implements IUnaryExpr {
    public static final UnaryExprHandler INSTANCE = new UnaryExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.UnaryExprContext ctx) {
        if (ctx.getChildCount() == 2) {
            return Implementation.UNARY;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.UnaryExprContext ctx, Function<Calc2Parser.PrimaryExprContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.UNARY.name());
    }

    @Override
    public Integer calculate(Calc2Parser.UnaryExprContext ctx, Map<String, Integer> vars, Function<Calc2Parser.UnaryExprContext, Integer> visitor) {
        String identifier = ParserRuleContextUtils.getIdentifier(ctx);
        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "-" -> -visitor.apply(ctx.unaryExpr());
            case "+" -> visitor.apply(ctx.unaryExpr());
            case "--" -> vars.merge(identifier, -1, Integer::sum);
            case "++" -> vars.merge(identifier, 1, Integer::sum);
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
